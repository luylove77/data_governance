package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * ç»´åº¦
 *
 * @author luyang
 * @since 2025-04-05
 */
@Data
@TableName("dm_dimension")
public class DmDimension implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    private Long id;


    /**
     * æ¨¡åž‹id
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

}