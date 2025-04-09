package com.luy.dwm.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmModifier;
import com.luy.dwm.model.service.DmModifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.luy.dwm.common.bean.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * é™�å®šè¯�è¡¨ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@RestController
@RequestMapping("/data-model/modifier")
public class DmModifierController {

    @Autowired
    DmModifierService dmModifierService;

    @PostMapping("/detail")
    public Result save(@RequestBody DmModifier dmModifier){
        dmModifier.setLastUpdateTime(new Date());
        dmModifier.setLastUpdateUserId(9999L);
        dmModifierService.saveOrUpdate(dmModifier);
        return Result.ok(dmModifier);
    }

    @GetMapping("/list")
    public Result getList(  QueryInfo queryInfo){
        //这个部分的查询放在服务层组织后面
        List<DmModifier> list = dmModifierService.getQueryList(queryInfo);
        Integer total = dmModifierService.getQueryTotal(queryInfo);

        return Result.ok(list,total);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id){
        return Result.ok(dmModifierService.getById(id));
    }

    @GetMapping("/options")
    public Result getOptions(@RequestParam(value = "modelId",required = false) Long modelId){
        List<Map<String, Object>> mapList = dmModifierService.listMaps(new QueryWrapper<DmModifier>()
                .select("id", "name_chn as name","name_eng as nameEng")
                .eq("is_deleted", "0")
                .eq(modelId != null, "model_id", modelId));

        return Result.ok(mapList);
    }



}
