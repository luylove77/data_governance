package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * æ•°æ�®è¡¨
 *
 * @author luyang
 * @since 2025-04-05
 */
@Data
@TableName("dm_table")
public class DmTable implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ä¸»é”®
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * æ¨¡åž‹id
     */
    private Long modelId;


    /**
     * æ•°æ�®åŸŸid
     */
    private Long domainId;


    /**
     * ä¸šåŠ¡è¿‡ç¨‹id
     */
    private Long busiProcessId;


    /**
     * ç»´åº¦id
     */
    private Long dimId;


    /**
     * ç»Ÿè®¡ç²’åº¦(nä¸ªç»´åº¦)
     */
    private String statDimIds;


    /**
     * ç»Ÿè®¡å‘¨æœŸ
     */
    private String statPeriod;


    /**
     * æ•°ä»“å±‚çº§
     */
    private String dwLevel;


    /**
     * å­˜å‚¨ç­–ç•¥
     */
    private String storageMode;


    /**
     * æ•°æ�®è¡¨å��
     */
    private String tableName;


    /**
     * æ•°æ�®è¡¨ä¸­æ–‡å��
     */
    private String tableNameChn;


    /**
     * schemaè¡¨å��
     */
    private String schemaName;


    /**
     * è§„åˆ™ç¼–ç �
     */
    private Long ruleId;


    /**
     * ç”Ÿå‘½å‘¨æœŸ(å¤©)
     */
    private Long lifecycleDays;


    /**
     * å®‰å…¨çº§åˆ«
     */
    private String securityLevel;


    /**
     * æŠ€æœ¯è´Ÿè´£äºº
     */
    private String tecOwner;


    /**
     * ä¸šåŠ¡è´Ÿè´£äºº
     */
    private String busiOwner;


    /**
     * çŠ¶æ€�
     */
    private String tableStatus;


    /**
     * å­˜å‚¨æ ¼å¼�
     */
    private String storageFormat;


    /**
     * åŽ‹ç¼©ç±»åž‹
     */
    private String compressType;


    /**
     * ç©ºå€¼æ›¿æ�¢
     */
    private String nullDefined;


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