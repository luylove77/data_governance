package com.luy.dwm.model.service;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmModifier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * é™�å®šè¯�è¡¨ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
public interface DmModifierService extends IService<DmModifier> {
    List<DmModifier> getQueryList(QueryInfo queryInfo);

    Integer getQueryTotal(QueryInfo queryInfo);
}
