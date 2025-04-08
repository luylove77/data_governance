package com.luy.dwm.model.controller;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.bean.DmMetric;
import com.luy.dwm.model.service.DmMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * æŒ‡æ ‡ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@RestController
@RequestMapping("/model/dmMetric")
public class DmMetricController {

    @Autowired
    DmMetricService dmMetricService;

//    @GetMapping("/list")
//    public Result getList(QueryInfo queryInfo) {
//
//
//        List<DmMetric> list = dmMetricService.getQueryList(queryInfo);
//        Integer total = dmMetricService.getQueryTotal(queryInfo);
//
//        //Result专门有封装返回两个参数的
//        return Result.ok(list, total);
//    }

}
