package com.luy.dwm.plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.plan.bean.DpNamingRule;
import com.luy.dwm.plan.service.DpNamingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * å‘½å��è§„åˆ™ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-03-27
 */
@RestController
@RequestMapping("/data-plan/naming")
public class DpNamingRuleController {

    @Autowired
    DpNamingRuleService dpNamingRuleService;

    @PostMapping("/detail")
    public Result saveNamingRule(@RequestBody DpNamingRule dpNamingRule) {
        dpNamingRule.setLastUpdateTime(new Date());
        dpNamingRule.setLastUpdateUserId(9999L);
        dpNamingRuleService.saveOrUpdate(dpNamingRule);
        return Result.ok(dpNamingRule);
    }

    @GetMapping("/list")
    public Result getList(){
        List<DpNamingRule> dpNamingRuleList = dpNamingRuleService.list();
        return Result.ok(dpNamingRuleList);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id){
        DpNamingRule dpNamingRule = dpNamingRuleService.getById(id);
        return Result.ok(dpNamingRule);
    }

    @GetMapping("/options")
    public Result getOptions(){
        QueryWrapper<DpNamingRule> queryWrapper = new QueryWrapper<DpNamingRule>().eq("is_deleted", "0")
                .select("id", "rule_name as name", "rule_body as ruleBody", "rule_prefix as rulePrefix");
        List<Map<String, Object>> mapList = dpNamingRuleService.listMaps(queryWrapper);//dpNamingRuleService.list(queryWrapper) 会返回javabean
        return Result.ok(mapList);
    }


}
