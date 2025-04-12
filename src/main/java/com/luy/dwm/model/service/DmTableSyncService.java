package com.luy.dwm.model.service;

import com.luy.dwm.model.bean.DmTableSync;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.thrift.TException;

import java.util.List;

/**
 * <p>
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
public interface DmTableSyncService extends IService<DmTableSync> {

    List<DmTableSync> getSyncList(String schemaName) throws Exception;

    void syncMeta(List<DmTableSync> tableSyncList) throws Exception;

    void syncDataInfo(List<DmTableSync> tableSyncList);
}
