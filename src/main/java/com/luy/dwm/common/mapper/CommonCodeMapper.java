package com.luy.dwm.common.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.common.bean.CommonCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("db1")
public interface CommonCodeMapper extends BaseMapper<CommonCode> {

    //getOptions方法根据parent_code_no返回code_no和code_name的list
//    @Select("select code_no,code_name from common_code where parent_code_no=#{parentCode} and is_deleted='0'")
//    public List<CommonCode> getOptions(String parentCode);
}
