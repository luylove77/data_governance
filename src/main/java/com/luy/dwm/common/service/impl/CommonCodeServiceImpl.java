package com.luy.dwm.common.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luy.dwm.common.bean.CommonCode;
import com.luy.dwm.common.mapper.CommonCodeMapper;
import com.luy.dwm.common.service.CommonCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@DS("db1")
public class CommonCodeServiceImpl extends ServiceImpl<CommonCodeMapper,CommonCode> implements CommonCodeService {
//    @Autowired
//    private CommonCodeMapper commonCodeMapper;

    //根据parent_code_no返回code_no和code_name的list
//    public List<CommonCode> getOptions(String parentCode) {
//
//        return commonCodeMapper.getOptions(parentCode);
//
//    }
}
