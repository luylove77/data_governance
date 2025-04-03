package com.luy.dwm.plan.service;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.plan.bean.DpBusiProcess;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * ä¸šåŠ¡è¿‡ç¨‹ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-02
 */
public interface DpBusiProcessService extends IService<DpBusiProcess> {
    List<DpBusiProcess> getQueryList(QueryInfo queryInfo);

    Integer getQueryTotal(QueryInfo queryInfo);
}
