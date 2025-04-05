package com.luy.dwm.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.service.DmDimensionService;
import com.luy.dwm.plan.bean.DpBusiProcess;
import com.luy.dwm.plan.service.DpBusiProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ç»´åº¦ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@RestController
@RequestMapping("/data-model/dim")
public class DmDimensionController {

    @Autowired
    DmDimensionService dmDimensionService;

    @PostMapping("/detail")
    public Result save(@RequestBody DmDimension dmDimension){
        dmDimension.setLastUpdateTime(new Date());
        dmDimension.setLastUpdateUserId(9999L);
        dmDimensionService.saveOrUpdate(dmDimension);

        return Result.ok(dmDimension);
    }

    @GetMapping("/list")
    public Result getList(QueryInfo queryInfo) {


        List<DmDimension> list = dmDimensionService.getQueryList(queryInfo);
        Integer total = dmDimensionService.getQueryTotal(queryInfo);

        //Result专门有封装返回两个参数的
        return Result.ok(list, total);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id) {
        return Result.ok(dmDimensionService.getById(id));
    }

    @GetMapping("/options")
    public Result getOptions(@RequestParam(value = "modelId",required = false) Long modelId){
        QueryWrapper<DmDimension> queryWrapper = new QueryWrapper<DmDimension>().eq("is_deleted", "0")
                .eq(modelId != null, "model_id", modelId)
                .select("id", "name_chn as name", "name_eng as nameEng");
        List<Map<String, Object>> mapList = dmDimensionService.listMaps(queryWrapper);
        return Result.ok(mapList);
    }

}
