package com.luy.dwm.model.service;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmMetric;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luy.dwm.model.bean.DmModifier;
import com.luy.dwm.model.bean.DmTable;

import java.util.List;

/**
 * <p>
 * æŒ‡æ ‡ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
public interface DmMetricService extends IService<DmMetric> {
    List<DmMetric> getQueryList(QueryInfo queryInfo);

    Integer getQueryTotal(QueryInfo queryInfo);

    Object getQueryLinkList(Long tableId);

    List<DmTable> getMetricLinkTable(Long metricId);
}
