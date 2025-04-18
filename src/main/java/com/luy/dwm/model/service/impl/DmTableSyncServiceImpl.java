package com.luy.dwm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.component.TableHdfsProcessor;
import com.luy.dwm.common.component.TableHiveProcessor;
import com.luy.dwm.common.constants.CommonCodes;
import com.luy.dwm.common.mapper.HiveJdbcMapper;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.bean.DmTableDataInfo;
import com.luy.dwm.model.bean.DmTableSync;
import com.luy.dwm.model.mapper.DmTableSyncMapper;
import com.luy.dwm.model.service.DmTableDataInfoService;
import com.luy.dwm.model.service.DmTableService;
import com.luy.dwm.model.service.DmTableSyncService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@Service
@DS("db1")
public class DmTableSyncServiceImpl extends ServiceImpl<DmTableSyncMapper, DmTableSync> implements DmTableSyncService {

    @Autowired
    TableHiveProcessor tableHiveProcessor;

    @Autowired
    DmTableService dmTableService;

    @Autowired
    DpDataWarehouseModelService dpDataWarehouseModelService;

    @Autowired
    HiveJdbcMapper hiveJdbcMapper;

    @Autowired
    DmTableDataInfoService dmTableDataInfoService;

    @Autowired
    TableHdfsProcessor tableHdfsProcessor;



    @Override
    public List<DmTableSync> getSyncList(String schemaName) throws Exception {
        // 1. 获取hive数据库表列表
        List<String> tableNameList = tableHiveProcessor.getTableNameList(schemaName);

        // 2 已经保存的同步信息列表，方便查转为map  key：tableName value:DmTableSync
        // key:dmTableSync->dmTableSync.getTableName() value:dmTableSync -> dmTableSync
        List<DmTableSync> dmTableSyncList = this.list(new QueryWrapper<DmTableSync>().eq("schema_name", schemaName));
        Map<String, DmTableSync> tableSyncMap = dmTableSyncList.stream().collect(Collectors.toMap(dmTableSync -> dmTableSync.getTableName(), dmTableSync -> dmTableSync));

        // 3 准备建模表的列表 key:tableName value:DmTable
        List<DmTable> dmTableList = dmTableService.list(new QueryWrapper<DmTable>().eq("schema_name", schemaName).eq("is_deleted", "0"));
        Map<String, DmTable> dmTableMap = dmTableList.stream().collect(Collectors.toMap(dmTable -> dmTable.getTableName(), dmTable -> dmTable));

        // 4 准备数仓模型的列表 key:shema value:DpDataWarehouse
        List<DpDataWarehouseModel> modelList = dpDataWarehouseModelService.list(new QueryWrapper<DpDataWarehouseModel>().eq("schema_name", schemaName).eq("is_deleted", "0"));
        Map<String, DpDataWarehouseModel> modelMap = modelList.stream().collect(Collectors.toMap(dpDataWarehouseModel -> dpDataWarehouseModel.getSchemaName(), dpDataWarehouseModel -> dpDataWarehouseModel));

        // 5 循环hive列表
        List<DmTableSync> dmTableSyncListForShow = new ArrayList<>();

        for (String tableName : tableNameList) {
            DmTableSync dmTableSync = new DmTableSync();
            if (tableSyncMap.containsKey(tableName)) {
                // 5 1 再根据表名查询已经保存过的同步信息(table_sync)
                dmTableSync = tableSyncMap.get(tableName);

            } else {
                // 5 2 如果没有查询同步信息，查询是否有同名的建模表
                if (dmTableMap.containsKey(tableName)) {
                    // 5 2 1 如果有建模表，查询建模表的schema_name
                    DmTable dmTable = dmTableMap.get(tableName);
                    dmTableSync.setModelId(dmTable.getModelId());
                    dmTableSync.setSchemaName(dmTable.getSchemaName());
                    dmTableSync.setTableId(dmTable.getId());
                    dmTableSync.setTableName(dmTable.getTableName());
                }else {
                    // 5 3 如果建模表没有查询到，查询是否有同名的数仓模型
                    DpDataWarehouseModel model = modelMap.get(schemaName);
                    if (model != null){
                        dmTableSync.setModelId(model.getId());

                    }
                }
            }
            dmTableSync.setSchemaName(schemaName);
            dmTableSync.setTableName(tableName);
            dmTableSyncListForShow.add(dmTableSync);
        }

        return dmTableSyncListForShow;
    }

    @Override
    public void syncMeta(List<DmTableSync> tableSyncList) throws TException {
       List<Long> tableIdList = tableSyncList.stream().filter(dmTableSync -> dmTableSync.getTableId() != null)
                       .map(dmTableSync -> dmTableSync.getTableId()).collect(Collectors.toList());

       Map<Long, DmTable> dmTableMap = new HashMap<>();

       if (tableIdList.size() > 0){
           List<DmTable> dmTableList = dmTableService.list(new QueryWrapper<DmTable>().in("id", tableIdList));
           //把dmTableList转为map 用tableId作为key 方便后续使用
           dmTableMap = dmTableList.stream().collect(Collectors.toMap(dmTable -> dmTable.getId(), dmTable -> dmTable));
       }


       // 每一次表保存是独立事件，所以用循环
        for (DmTableSync dmTableSync : tableSyncList) {
            DmTable dmTable=null;
            if (dmTableSync.getTableId() != null) {
                dmTable = dmTableMap.get(dmTableSync.getTableId());
            }else {
                dmTable = new DmTable();
                dmTable.setSchemaName(dmTableSync.getSchemaName());
                dmTable.setTableName(dmTableSync.getTableName());
                dmTable.setModelId(dmTableSync.getModelId());
            }
            //2 同步hive元数据
            tableHiveProcessor.syncTableMeta(dmTable);

            System.out.println("dmTable = " + dmTable);

            //3 保存dmTable建模表, saveTableAll有保存字段
            dmTable.setTableStatus(CommonCodes.TABLE_STATUS_ADDED);
            dmTableService.saveTableAll(dmTable);

            //4 保存对应的同步信息
            dmTableSync.setTableId(dmTable.getId());
            dmTableSync.setLastSyncMetaTime(new Date());
            dmTableSync.setLastSyncMetaUserId(9999L);
            //赋值完要存一下
            this.saveOrUpdate(dmTableSync);

        }


    }

    /**
     *  tableFormattedInfo变量 里面存的数据
     *       data_type    comment col_name
     * 第一行 data_type    comment col_name
     *       string       活动id   id
     *       ...
     *       numFiles      1
     *       numPartitions 2
     *       numRows       4
     *       rawDataSize   443
     *       totalSize     12
     */

    @Override
    public void syncDataInfo(List<DmTableSync> tableSyncList) throws Exception {
        for (DmTableSync dmTableSync : tableSyncList) {
            //强制统计数据信息
            hiveJdbcMapper.analyzeTable(dmTableSync.getSchemaName(),dmTableSync.getTableName());
            DmTableDataInfo dmTableDataInfo = new DmTableDataInfo();
            //提取统计信息
            List<Map<String, Object>> tableFormattedInfoList = hiveJdbcMapper.getTableFormattedInfo(dmTableSync.getSchemaName(), dmTableSync.getTableName());

            // 遍历tableFormattedInfoList
            for (Map<String, Object> formatMap : tableFormattedInfoList) {
                if (formatMap.get("data_type")==null){
                    // 如果为空则跳过
                    continue;
                }
                String dataType = formatMap.get("data_type").toString().trim();
                if (dataType.equals("numFiles")) { //文件大小
                    String numFiles = formatMap.get("comment").toString().trim();
                    dmTableDataInfo.setNumFiles(Long.valueOf(numFiles));
                } else if (dataType.equals("numPartitions")) {
                    String numPartitions = formatMap.get("comment").toString().trim();
                    dmTableDataInfo.setNumPartitions(Long.valueOf(numPartitions));
                } else if (dataType.equals("rawDataSize")) {
                    String rawDataSize = formatMap.get("comment").toString().trim();
                    dmTableDataInfo.setRawDataSize(Long.valueOf(rawDataSize));
                } else if (dataType.equals("numRows")) {
                    String numRows = formatMap.get("comment").toString().trim();
                    dmTableDataInfo.setNumRows(Long.valueOf(numRows));
                } else if (dataType.equals("totalSize")) {
                    String totalSize = formatMap.get("comment").toString().trim();
                    dmTableDataInfo.setDataSize(Long.valueOf(totalSize));
                }

            }

            // 计算 压缩比 , 4舍5入, bigdecimal不会丢精度, double会丢精度
            // 压缩比 = 数据大小 / 原始数据大小
            if(dmTableDataInfo.getRawDataSize()!=null&&dmTableDataInfo.getRawDataSize()!=0L){
                dmTableDataInfo.setCompressRatio(BigDecimal.valueOf(dmTableDataInfo.getDataSize()).divide(BigDecimal.valueOf(dmTableDataInfo.getRawDataSize()),2,BigDecimal.ROUND_HALF_UP));
            }

            // 计算 文件平均大小
            // 文件平均大小 = 数据大小 / 文件数量 , 单位字节很小了不需要保留小数
            if(dmTableDataInfo.getNumFiles()!=null&&dmTableDataInfo.getNumFiles()!=0L){
                dmTableDataInfo.setFileSizeAvg(BigDecimal.valueOf(dmTableDataInfo.getDataSize()).divide(BigDecimal.valueOf(dmTableDataInfo.getNumFiles()),2,BigDecimal.ROUND_HALF_UP).longValue());
            }

            dmTableDataInfo.setTableId(dmTableSync.getTableId());
            dmTableDataInfo.setTableName(dmTableSync.getTableName());
            dmTableDataInfo.setSchemaName(dmTableSync.getSchemaName());
            dmTableDataInfo.setLastSyncInfoTime(new Date());


            //获得hdfs的相关信息，包括副本大小，最后访问时间，最后修改时间
            Table table = tableHiveProcessor.getTable(dmTableSync.getSchemaName(), dmTableSync.getTableName());
            tableHdfsProcessor.getHdfsFileInfo(table,dmTableDataInfo);

            // 保存数据信息
            dmTableDataInfoService.remove(new QueryWrapper<DmTableDataInfo>()
                    .eq("table_name",dmTableDataInfo.getTableName())
                    .eq("schema_name",dmTableDataInfo.getSchemaName()));

            dmTableDataInfoService.saveOrUpdate(dmTableDataInfo);

            // 保存同步信息
            dmTableSync.setLastSyncInfoTime(new Date());
            this.saveOrUpdate(dmTableSync);

        }
    }

    public void syncDataInfoForScheduler() throws Exception {
        //1 查询出需要同步信息的列表，勾上的
        List<DmTableSync> dmTableSyncList = this.list(new QueryWrapper<DmTableSync>().eq("is_sync_info", "1"));
        //2 遍历该列表，逐个同步信息
        syncDataInfo(dmTableSyncList);
    }
}
