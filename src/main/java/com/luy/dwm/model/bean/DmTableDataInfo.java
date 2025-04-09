package com.luy.dwm.model.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * æ•°æ�®ä¿¡æ�¯è¡¨
 *
 * @author luyang
 * @since 2025-04-09
 */
@Data
@TableName("dm_table_data_info")
public class DmTableDataInfo implements Serializable {

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
     * æ•°æ�®è¡¨å��
     */
    private String tableName;


    /**
     * schemaå��
     */
    private String schemaName;


    /**
     * æ–‡ä»¶æ•°
     */
    private Long numFiles;


    /**
     * è¡Œæ•°
     */
    private Long numRows;


    /**
     * åˆ†åŒºæ•°
     */
    private Long numPartitions;


    /**
     * å¹³å�‡æ–‡ä»¶å¤§å°�
     */
    private Long fileSizeAvg;


    /**
     * åŽ‹ç¼©æ¯”
     */
    private BigDecimal compressRatio;


    /**
     * æœªåŽ‹ç¼©æ•°æ�®å¤§å°�(å­—èŠ‚)
     */
    private Long rawDataSize;


    /**
     * æ•°æ�®å¤§å°�(å­—èŠ‚)
     */
    private Long dataSize;


    /**
     * æ•°æ�®å¤§å°�(å­—èŠ‚)å�«å‰¯æœ¬æ•°
     */
    private Long dataSizeAllRep;


    /**
     * æœ€å�Žä¿®æ”¹æ—¶é—´
     */
    private Date dataLastModifyTime;


    /**
     * æœ€å�Žè®¿é—®æ—¶é—´
     */
    private Date dataLastAccessTime;


    /**
     * æœ€å�Žå�Œæ­¥æ•°æ�®ä¿¡æ�¯æ—¶é—´
     */
    private Date lastSyncInfoTime;

}