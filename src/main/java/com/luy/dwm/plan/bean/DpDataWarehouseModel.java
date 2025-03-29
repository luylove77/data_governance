package com.luy.dwm.plan.bean;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author luyang
 * @since 2025-03-29
 */
@Data
public class DpDataWarehouseModel  {


    private Long id;

    private String modelName;

    private String schemaName;

    private String remark;

    private Long lastUpdateUserId;

    private Date lastUpdateTime;

    private String isDeleted;

}