package com.luy.dwm.plan.controller;

import com.luy.dwm.common.bean.Result;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.luy.dwm.plan.service.DpDataDomainService;
import com.luy.dwm.plan.service.DpNamingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @PostMapping("/detail")
    public Result saveDomain(@RequestBody DpDataDomain dpDataDomain){
        dpDataDomain.setLastUpdateTime(new Date());
        dpDataDomain.setLastUpdateUserId(9999L);
        dpDataDomainService.saveOrUpdate(dpDataDomain);

        return Result.ok(dpDataDomain);
    }

}
