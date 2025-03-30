package com.luy.dwm.plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.common.component.TableHiveProcessor;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luyang
 * @since 2025-03-29
 */
@RestController
@RequestMapping("/data-plan/model")
public class DpDataWarehouseModelController {
    @Autowired
    DpDataWarehouseModelService dpDataWarehouseModelService;

    @Autowired
    TableHiveProcessor tableHiveProcessor;

    @PostMapping("/detail")
    public Result saveDetail(@RequestBody DpDataWarehouseModel dpDataWarehouseModel) {
        dpDataWarehouseModel.setLastUpdateTime(new Date());
        dpDataWarehouseModel.setLastUpdateUserId(9999L);
        dpDataWarehouseModelService.saveOrUpdate(dpDataWarehouseModel);
        return Result.ok(dpDataWarehouseModel);
    }

    @GetMapping("/list")
    public Result getList(){
        List<DpDataWarehouseModel> modelList = dpDataWarehouseModelService.list(new QueryWrapper<DpDataWarehouseModel>().eq("is_deleted", "0"));
        return Result.ok(modelList);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id){
        DpDataWarehouseModel dpDataWarehouseModel = dpDataWarehouseModelService.getById(id);
        return Result.ok(dpDataWarehouseModel);
    }

    @GetMapping("/options")
    public Result getOptions(){
        QueryWrapper<DpDataWarehouseModel> queryWrapper = new QueryWrapper<DpDataWarehouseModel>().eq("is_deleted", "0")
                .select("id", "model_name as name", "schema_name as nameEng");
        List<Map<String, Object>> mapList = dpDataWarehouseModelService.listMaps(queryWrapper);
        return Result.ok(mapList);
    }

    @PostMapping("/detail/hive")
    public Result saveHive(@RequestBody DpDataWarehouseModel dpDataWarehouseModel){

        try {
            tableHiveProcessor.createDatabaseToHive(dpDataWarehouseModel.getSchemaName());
        } catch (TException e) {
            if(e.getLocalizedMessage().indexOf("exists") >= 0){
                return Result.error("数据库已经存在");
            }
            throw new RuntimeException(e);
        }
        return Result.ok();
    }

}
