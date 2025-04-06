package com.luy.dwm.common.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayTypeHandler extends BaseTypeHandler<Long[]> {

    //java对象属性Long[] 转为varchar(string)
    //map(String::valueOf) 等于 a -> String.valueOf(a) 把数组里面的每个值转为string
    //i代表数据库第几个值
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType) throws SQLException {
        String value = Arrays.stream(parameter).map(String::valueOf).collect(Collectors.joining(","));
        ps.setString(i, value);
    }

    //从数据库字段string转为Java Bean属性
    @Override
    public Long[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String stringValue = rs.getString(columnName);

        if(stringValue != null){
            String[] split = stringValue.split(",");
            return Arrays.stream(split).map(Long::valueOf).toArray(Long[]::new);
        }

        return new Long[0];
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String stringValue = rs.getString(columnIndex);

        if(stringValue != null){
            String[] split = stringValue.split(",");
            return Arrays.stream(split).map(Long::valueOf).toArray(Long[]::new);
        }

        return new Long[0];
    }

    @Override
    public Long[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String stringValue = cs.getString(columnIndex);

        if(stringValue != null){
            String[] split = stringValue.split(",");
            return Arrays.stream(split).map(Long::valueOf).toArray(Long[]::new);
        }

        return new Long[0];
    }
}
