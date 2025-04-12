package com.luy.dwm.common.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
@DS("db2")
public interface HiveJdbcMapper {

    //执行hive统计
    @Insert("analyze table ${schemaName}.${tableName} compute statistics")
    void analyzeTable(@Param("schemaName") String schemaName,@Param("tableName") String tableName);

    //读数据
    @Select("describe formatted ${schemaName}.${tableName}")
    List<Map<String,Object>> getTableFormattedInfo(@Param("schemaName") String schemaName,@Param("tableName") String tableName);

}
