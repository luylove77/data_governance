package com.luy.dwm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.component.TableHiveProcessor;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.bean.DmTableSync;
import com.luy.dwm.model.mapper.DmTableSyncMapper;
import com.luy.dwm.model.service.DmTableService;
import com.luy.dwm.model.service.DmTableSyncService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
                    if (dmTable != null){
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
            }
            dmTableSync.setSchemaName(schemaName);
            dmTableSync.setTableName(tableName);
            dmTableSyncListForShow.add(dmTableSync);
        }

        return dmTableSyncListForShow;
    }
}
