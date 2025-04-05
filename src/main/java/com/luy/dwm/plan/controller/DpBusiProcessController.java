package com.luy.dwm.plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.plan.bean.DpBusiProcess;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.luy.dwm.plan.service.DpBusiProcessService;
import com.luy.dwm.plan.service.DpDataDomainService;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务过程 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-02
 */
@RestController
@RequestMapping("/data-plan/busi")
public class DpBusiProcessController {
    @Autowired
    DpBusiProcessService dpBusiProcessService;

    @PostMapping("/detail")
    public Result save(@RequestBody DpBusiProcess dpBusiProcess){
        dpBusiProcess.setLastUpdateTime(new Date());
        dpBusiProcess.setLastUpdateUserId(9999L);
        dpBusiProcessService.saveOrUpdate(dpBusiProcess);

        return Result.ok(dpBusiProcess);
    }

    @GetMapping("/list")
    public Result getList(QueryInfo queryInfo) {


        List<DpBusiProcess> list = dpBusiProcessService.getQueryList(queryInfo);
        Integer total = dpBusiProcessService.getQueryTotal(queryInfo);

        //Result专门有封装返回两个参数的
        return Result.ok(list, total);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable("id") Long id) {
        return Result.ok(dpBusiProcessService.getById(id));
    }

    @GetMapping("/options")
    public Result getOptions(@RequestParam(value = "modelId",required = false) Long modelId){
        QueryWrapper<DpBusiProcess> queryWrapper = new QueryWrapper<DpBusiProcess>().eq("is_deleted", "0")
                .eq(modelId != null, "model_id", modelId)
                .select("id", "name_chn as name", "name_eng as nameEng");
        List<Map<String, Object>> mapList = dpBusiProcessService.listMaps(queryWrapper);
        return Result.ok(mapList);
    }


}
