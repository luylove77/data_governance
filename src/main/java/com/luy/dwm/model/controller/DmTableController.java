package com.luy.dwm.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.service.DmDimensionService;
import com.luy.dwm.model.service.DmTableColumnService;
import com.luy.dwm.model.service.DmTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * æ•°æ�®è¡¨ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/data-model/table")
public class DmTableController {

    @Autowired
    DmTableService dmTableService;

    @Autowired
    DmTableColumnService dmTableColumnService;

    @PostMapping("/detail")
    public Result save(@RequestBody DmTable dmTable){
        dmTableService.saveTableAll(dmTable);
        return Result.ok(dmTable);
    }

    @GetMapping("/list")
    public Result getList(QueryInfo queryInfo) {


        List<DmTable> list = dmTableService.getQueryList(queryInfo);
        Integer total = dmTableService.getQueryTotal(queryInfo);

        //Result专门有封装返回两个参数的
        return Result.ok(list, total);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id) {
        DmTable dmTable = dmTableService.getTableAll(id);
        return Result.ok(dmTable);
    }

    @PostMapping("/hive/{id}")
    public Result submitToHive(@PathVariable("id") Long tableId) {
        try {
            dmTableService.submitToHive(tableId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Hive建表失败");
        }
        return Result.ok();
    }
//
//    @GetMapping("/options")
//    public Result getOptions(@RequestParam(value = "modelId",required = false) Long modelId){
//        QueryWrapper<DmDimension> queryWrapper = new QueryWrapper<DmDimension>().eq("is_deleted", "0")
//                .eq(modelId != null, "model_id", modelId)
//                .select("id", "name_chn as name", "name_eng as nameEng");
//        List<Map<String, Object>> mapList = dmDimensionService.listMaps(queryWrapper);
//        return Result.ok(mapList);
//    }


}
