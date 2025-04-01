package com.luy.dwm.plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.luy.dwm.plan.service.DpDataDomainService;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import com.luy.dwm.plan.service.DpNamingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * æ•°æ�®åŸŸ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-03-31
 */
@RestController
@RequestMapping("/data-plan/domain")
public class DpDataDomainController {
    @Autowired
    DpDataDomainService dpDataDomainService;

    @Autowired
    DpDataWarehouseModelService dpDataWarehouseModelService;

    @PostMapping("/detail")
    public Result saveDomain(@RequestBody DpDataDomain dpDataDomain){
        dpDataDomain.setLastUpdateTime(new Date());
        dpDataDomain.setLastUpdateUserId(9999L);
        dpDataDomainService.saveOrUpdate(dpDataDomain);

        return Result.ok(dpDataDomain);
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(value = "modelId", required = false) Long modelId) {
        QueryWrapper<DpDataDomain> queryWrapper = new QueryWrapper<DpDataDomain>().eq(modelId != null, "model_id", modelId)
                .eq("is_deleted", "0");

        List<DpDataDomain> domainList = dpDataDomainService.getDomainList(modelId);

        return Result.ok(domainList);
    }

}
