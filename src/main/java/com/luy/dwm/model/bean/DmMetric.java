package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luy.dwm.common.util.ArrayTypeHandler;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * æŒ‡æ ‡
 *
 * @author luyang
 * @since 2025-04-08
 */
@Data
@TableName("dm_metric")
public class DmMetric implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * æŒ‡æ ‡å��ç§°
     */
    private String metricName;


    /**
     * æŒ‡æ ‡ç±»åž‹
     */
    private String metricType;


    /**
     * æ¨¡åž‹id
     */
    private Long modelId;


    /**
     * æ•°æ�®åŸŸid
     */
    private Long domainId;


    /**
     * æ•°æ�®è¡¨id
     */
    private Long linkTableId;


    /**
     * å­—æ®µid
     */
    private Long linkColumnId;


    /**
     * ç²’åº¦_ç»´åº¦id
     */
    @TableField(typeHandler = ArrayTypeHandler.class)
    private Long[] linkDimIds;


    /**
     * å…³è�”åŽŸå­�æŒ‡æ ‡
     */
    @TableField(typeHandler = ArrayTypeHandler.class)
    private Long[] linkAtomicMetricIds;


    /**
     * ç»Ÿè®¡å‘¨æœŸ(æ´¾ç”Ÿ)
     */
    private String linkStatPeriod;


    /**
     * ä¿®é¥°è¯�id(æ´¾ç”Ÿ)
     */
    @TableField(typeHandler = ArrayTypeHandler.class)
    private Long[] linkModifierIds;


    /**
     * è®¡ç®—å�£å¾„(è¡�ç”Ÿ)
     */
    private String linkCalcDesc;


    /**
     * å¤‡æ³¨
     */
    private String remark;


    /**
     * æœ€å�Žä¿®æ”¹äººid
     */
    private Long lastUpdateUserId;


    /**
     * æœ€å�Žä¿®æ”¹æ—¶é—´
     */
    private Date lastUpdateTime;


    /**
     * æ˜¯å�¦åˆ é™¤
     */
    private String isDeleted;

    @TableField(exist = false)
    private String modelName;

    @TableField(exist = false)
    private String domainName;

    @TableField(exist = false)
    private String metricTypeName;

    @TableField(exist = false)
    private String columnComment;

    @TableField(exist = false)
    private String columnName;

    @TableField(exist = false)
    private List<DmTable> linkTableList;

}