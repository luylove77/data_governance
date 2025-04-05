package com.luy.dwm.plan.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * ä¸šåŠ¡è¿‡ç¨‹
 *
 * @author luyang
 * @since 2025-04-01
 */
@Data
@TableName("dp_busi_process")
public class DpBusiProcess implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * æ•°æ�®åŸŸid
     */
    private Long domainId;


    /**
     * æ•°ä»“æ¨¡åž‹id
     */
    private Long modelId;


    /**
     * ä¸­æ–‡å��ç§°
     */
    private String nameChn;


    /**
     * è‹±æ–‡å��ç§°
     */
    private String nameEng;


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

}