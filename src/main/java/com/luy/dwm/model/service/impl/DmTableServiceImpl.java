package com.luy.dwm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.component.TableHiveProcessor;
import com.luy.dwm.common.util.SqlUtil;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.bean.DmTableColumn;
import com.luy.dwm.model.mapper.DmTableMapper;
import com.luy.dwm.model.service.DmTableColumnService;
import com.luy.dwm.model.service.DmTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * æ•°æ�®è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Service
@DS("db1")
public class DmTableServiceImpl extends ServiceImpl<DmTableMapper, DmTable> implements DmTableService {

    @Autowired
    DmTableColumnService dmTableColumnService;

    @Autowired
    TableHiveProcessor tableHiveProcessor;

    @Transactional
    public void saveTableAll(DmTable dmTable){
        dmTable.setLastUpdateTime(new Date());
        dmTable.setLastUpdateUserId(9999L);
        this.saveOrUpdate(dmTable);
        int seq=1;
        if(dmTable.getTableColumns()!=null){
            for(DmTableColumn tableColumn:dmTable.getTableColumns()){
                tableColumn.setTableId(dmTable.getId());
                tableColumn.setSeq(seq++);
                tableColumn.setIsPartitionCol("0");
                tableColumn.setLastUpdateTime(new Date());
                tableColumn.setLastUpdateUserId(9999L);
            }
        }

        seq=1;
        if(dmTable.getPartitionColumns()!=null){
            for(DmTableColumn partitionColumn:dmTable.getPartitionColumns()){
                partitionColumn.setTableId(dmTable.getId());
                partitionColumn.setSeq(seq++);
                partitionColumn.setIsPartitionCol("1");
                partitionColumn.setLastUpdateTime(new Date());
                partitionColumn.setLastUpdateUserId(9999L);
            }
        }
        // saveOrUpdateBatch只能新增或者修改，不能删除，所以删除的字段要先删除
        dmTableColumnService.saveOrUpdateBatch(dmTable.getTableColumns());
        dmTableColumnService.saveOrUpdateBatch(dmTable.getPartitionColumns());

        // 清理掉已经删除的字段
        List<Long> colsIdList = dmTable.getTableColumns().stream().map(dmTableColumn -> dmTableColumn.getId()).collect(Collectors.toList());

        // 可能没分区
        if(dmTable.getPartitionColumns()!=null) {
            List<Long> partColsIdList = dmTable.getPartitionColumns().stream().map(dmTableColumn -> dmTableColumn.getId()).collect(Collectors.toList());
            colsIdList.addAll(partColsIdList);
        }

        UpdateWrapper<DmTableColumn> updateWrapper = new UpdateWrapper<DmTableColumn>().set("is_deleted", "1")
                .eq("table_id", dmTable.getId())
                .notIn("id", colsIdList);
        dmTableColumnService.update(updateWrapper);

    }

    @Override
    public List<DmTable> getQueryList(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }
        if(queryInfo.getTableNameQuery()!=null&&queryInfo.getTableNameQuery().trim().length()>0){
            condition+="and ( t.table_name like '%"+ SqlUtil.filterUnsafeSql(queryInfo.getTableNameQuery())+"%' " +
                    "or t.table_name_chn like '%"+SqlUtil.filterUnsafeSql(queryInfo.getTableNameQuery())+"%' )";
        }
        condition+= queryInfo.getLimitSQL();

        List<DmTable> list = this.baseMapper.selectQueryList(condition);


        return list;
    }

    @Override
    public Integer getQueryTotal(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }
        if(queryInfo.getTableNameQuery()!=null&&queryInfo.getTableNameQuery().trim().length()>0){
            condition+="and ( t.table_name like '%"+queryInfo.getTableNameQuery()+"%' " +
                    "or t.table_name_chn like '%"+queryInfo.getTableNameQuery()+"%' )";
        }

        Integer total = this.baseMapper.selectQueryCount(condition);

        return total;
    }

    @Override
    public DmTable getTableAll(Long id) {
        DmTable dmTable = this.getById(id);
        List<DmTableColumn> tableColumns = dmTableColumnService.list(new QueryWrapper<DmTableColumn>()
                .eq("is_deleted", "0")
                .eq("table_id", id)
                .eq("is_partition_col", "0")
                .orderByAsc("seq"));
        dmTable.setTableColumns(tableColumns);

        List<DmTableColumn> ptableColumns = dmTableColumnService.list(new QueryWrapper<DmTableColumn>()
                .eq("is_deleted", "0")
                .eq("table_id", id)
                .eq("is_partition_col", "1")
                .orderByAsc("seq"));
        dmTable.setPartitionColumns(ptableColumns);

        return dmTable;

    }

    @Override
    public void submitToHive(Long tableId) throws TException {
        //提取dmTable对象
        DmTable dmTable = getTableAll(tableId);
        tableHiveProcessor.createTableToHive(dmTable);

    }
}
