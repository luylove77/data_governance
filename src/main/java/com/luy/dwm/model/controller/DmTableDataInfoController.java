package com.luy.dwm.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.bean.DmTableDataInfo;
import com.luy.dwm.model.service.DmTableDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * æ•°æ�®ä¿¡æ�¯è¡¨ 前端控制器
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/data-model/datainfo")
public class DmTableDataInfoController {

    @Autowired
    DmTableDataInfoService dmTableDataInfoService;

    @GetMapping("/detail/{tableId}")
    public Result getDetail(@PathVariable("tableId") Long tableId){
        DmTableDataInfo tableDataInfo = dmTableDataInfoService.getOne(new QueryWrapper<DmTableDataInfo>()
                .eq("table_id", tableId));

        return Result.ok(tableDataInfo);
    }

}
