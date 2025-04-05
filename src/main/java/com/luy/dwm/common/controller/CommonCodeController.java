package com.luy.dwm.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luy.dwm.common.bean.CommonCode;
import com.luy.dwm.common.bean.Result;
import com.luy.dwm.common.service.impl.CommonCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/data-common/code")
public class CommonCodeController {

    @Autowired
    CommonCodeServiceImpl commonCodeService;

    @GetMapping("/options")
    public Result getOptions(@RequestParam("pCode") String parentCode){
        QueryWrapper<CommonCode> queryWrapper = new QueryWrapper<CommonCode>()
                .select("code_no as id","code_name as name","ext_name as extName")
                .eq("parent_code_no",parentCode)
                .eq("is_deleted","0");
        //commonCodeService.list(queryWrapper) 会返回javabean

        //listMaps只会返回select列map，不返回javabean
        List<Map<String, Object>> maps = commonCodeService.listMaps(queryWrapper);
        return Result.ok(maps);
    }
}
