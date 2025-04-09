package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * é™�å®šè¯�è¡¨
 *
 * @author luyang
 * @since 2025-04-08
 */
@Data
@TableName("dm_modifier")
public class DmModifier implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * ä¸­æ–‡å��ç§°
     */
    private String nameChn;


    /**
     * è‹±æ–‡å��ç§°
     */
    private String nameEng;


    /**
     * æ¨¡åž‹id
     */
    private Long modelId;


    /**
     * ä¸šåŠ¡å�£å¾„
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

}