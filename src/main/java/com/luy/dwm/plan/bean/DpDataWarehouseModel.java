package com.luy.dwm.plan.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author luyang
 * @since 2025-03-29
 */
@Data
@TableName("dp_data_warehouse_model")
public class DpDataWarehouseModel  {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String modelName;

    private String schemaName;

    private String remark;

    private Long lastUpdateUserId;

    private Date lastUpdateTime;

    private String isDeleted;

}