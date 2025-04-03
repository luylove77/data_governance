package com.luy.dwm.common.bean;

import lombok.Data;

//查询条件封装为对象
@Data
public class QueryInfo  {

    Long modelId;

    String tableNameQuery;

    String metricName;

    int pageSize=20;

    int pageNo=1;

    //在方法中拼接SQL的limit语句
    //(pageNo - 1) * pageSize：计算当前页数据在数据库中的起始位置。pageNo 表示当前页码，
    // pageSize 表示每页显示的数据数量。在 SQL 中，LIMIT 子句的起始位置是从 0 开始计数的，
    // 所以第一页的起始位置是 0，第二页的起始位置是 (2 - 1) * pageSize，以此类推。
    // 比如limit 0,10 表示当前页为第一页, 每页10行
    // 默认值是显示第一页，20条

    public String getLimitSQL(){
        return " limit "+(pageNo-1)*pageSize+","+pageSize;
    }
}
