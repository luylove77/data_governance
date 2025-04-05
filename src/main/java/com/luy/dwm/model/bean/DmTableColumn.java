package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * å­—æ®µè¡¨
 *
 * @author luyang
 * @since 2025-04-05
 */
@Data
@TableName("dm_table_column")
public class DmTableColumn implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * æ•°æ�®è¡¨id
     */
    private Long tableId;


    /**
     * å­—æ®µå��ç§°
     */
    private String columnName;


    /**
     * å­—æ®µä¸­æ–‡å��ç§°
     */
    private String columnComment;


    /**
     * æ˜¯å�¦æ˜¯åˆ†åŒºå­—æ®µ
     */
    private String isPartitionCol;


    /**
     * æ•°æ�®ç±»åž‹
     */
    private String dataType;


    /**
     * æ¬¡åº�
     */
    private Integer seq;


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