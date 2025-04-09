package com.luy.dwm.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.bean.DmMetric;
import com.luy.dwm.model.bean.DmMetric;
import com.luy.dwm.model.service.DmMetricService;
import com.luy.dwm.model.service.DmMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * æŒ‡æ ‡ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@RestController
@RequestMapping("/data-model/metric")
public class DmMetricController {


    @Autowired
    DmMetricService dmMetricService;

    @PostMapping("/detail")
    public Result save(@RequestBody DmMetric dmMetric){
        dmMetric.setLastUpdateTime(new Date());
        dmMetric.setLastUpdateUserId(9999L);
        dmMetricService.saveOrUpdate(dmMetric);
        return Result.ok(dmMetric);
    }

    @GetMapping("/list")
    public Result getList(  QueryInfo queryInfo){
        //这个部分的查询放在服务层组织后面
        List<DmMetric> list = dmMetricService.getQueryList(queryInfo);
        Integer total = dmMetricService.getQueryTotal(queryInfo);

        return Result.ok(list,total);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id){
        return Result.ok(dmMetricService.getById(id));
    }

    @GetMapping("/options")
    public Result getOptions(@RequestParam(value = "modelId",required = false) Long modelId){
        List<Map<String, Object>> mapList = dmMetricService.listMaps(new QueryWrapper<DmMetric>()
                .select("id", "metric_name as name")
                .eq("is_deleted", "0")
                .eq(modelId != null, "model_id", modelId));

        return Result.ok(mapList);
    }

}
