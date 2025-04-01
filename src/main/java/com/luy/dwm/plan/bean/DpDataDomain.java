package com.luy.dwm.plan.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * æ•°æ�®åŸŸ
 *
 * @author luyang
 * @since 2025-03-31
 */
@Data
@TableName("dp_data_domain")
public class DpDataDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nameChn;

    private String nameEng;

    private Long modelId;

    private String remark;

    private Long lastUpdateUserId;

    private Date lastUpdateTime;

    private String isDeleted;

    @TableField(exist = false)
    private String modelName;

}