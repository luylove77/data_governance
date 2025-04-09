package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨
 *
 * @author luyang
 * @since 2025-04-09
 */
@Data
@TableName("dm_table_sync")
public class DmTableSync implements Serializable {

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
     * æ•°ä»“æ¨¡åž‹id
     */
    private Long modelId;


    /**
     * æ•°æ�®è¡¨å��
     */
    private String tableName;


    /**
     * schemaå��
     */
    private String schemaName;


    /**
     * æ˜¯å�¦æ¯�æ—¥å�Œæ­¥æ•°æ�®ä¿¡æ�¯
     */
    private String isSyncInfo;


    /**
     * æœ€å�Žå�Œæ­¥è¡¨ç»“æž„æ—¶é—´
     */
    private Date lastSyncMetaTime;


    /**
     * æœ€å�Žå�Œæ­¥æ•°æ�®ç”¨æˆ·
     */
    private Long lastSyncMetaUserId;


    /**
     * æœ€å�Žå�Œæ­¥æ•°æ�®ä¿¡æ�¯æ—¶é—´
     */
    private Date lastSyncInfoTime;

}