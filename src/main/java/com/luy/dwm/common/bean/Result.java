package com.luy.dwm.common.bean;

import lombok.*;
import org.apache.ibatis.annotations.ConstructorArgs;

import java.util.List;

@Data
public class Result {

    /**
     * 总数，用于表示数据的总量。
     */
    Integer total;

    /**
     * 状态码，默认值为200，表示成功。
     */
    Integer status = 200;

    /**
     * 错误信息，用于描述错误原因。
     */
    String errorMsg;

    /**
     * 数据对象，存储实际返回的数据。
     */
    Object data;

    /**
     * 静态方法，创建并返回一个表示错误的Result对象。
     * @param msg 错误信息
     * @return 表示错误的Result对象
     */
    public static Result error(String msg){
        Result result = new Result();
        result.setStatus(600);
        result.setErrorMsg(msg);
        return result;
    }

    /**
     * 静态方法，创建并返回一个表示成功且包含数据的Result对象。
     * @param data 返回的数据
     * @return 表示成功的Result对象
     */
    public static Result ok(Object data){
        Result result = new Result();
        result.setData(data);
        return result;
    }

    /**
     * 静态方法，创建并返回一个表示成功的Result对象，不包含数据。
     * @return 表示成功的Result对象
     */
    public static Result ok(){
        return new Result();
    }

    /**
     * 静态方法，创建并返回一个表示成功且包含数据和总数的Result对象。
     * @param data 返回的数据
     * @param total 数据的总量
     * @return 表示成功的Result对象
     */
    public static Result ok(Object data, int total){
        Result result = new Result();
        result.setData(data);
        result.setTotal(total);
        return result;
    }
}