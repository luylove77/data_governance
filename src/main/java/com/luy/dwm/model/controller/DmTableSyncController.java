package com.luy.dwm.model.controller;

import com.luy.dwm.common.bean.Result;
import com.luy.dwm.common.component.TableHiveProcessor;
import com.luy.dwm.model.bean.DmTableSync;
import com.luy.dwm.model.service.DmTableSyncService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/data-model/sync")
public class DmTableSyncController {

    @Autowired
    TableHiveProcessor tableHiveProcessor;

    @Autowired
    DmTableSyncService dmTableSyncService;

    @GetMapping("/schema/options")
    public Result getSchemaNameOptions(){
        List<String> schemaNameList = null;
        try {
            schemaNameList = tableHiveProcessor.getDatabaseNameList();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取hive数据库列表失败");
        }
        return Result.ok(schemaNameList);
    }

    @GetMapping("/list")
    public Result getSyncList(@RequestParam("schemaName") String schemaName){
        List<DmTableSync> dmTableSyncList = null;
        try {
            dmTableSyncList = dmTableSyncService.getSyncList(schemaName);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取hive数据表列表失败");
        }

        return Result.ok(dmTableSyncList);
    }

    @PostMapping("/meta")
    public Result syncMeta(@RequestBody List<DmTableSync> tableSyncList){
        try {
            dmTableSyncService.syncMeta(tableSyncList);
        } catch (TException e) {
            e.printStackTrace();
            return Result.error("同步hive元数据失败");
        }
        return Result.ok();
    }
}
