package com.luy.dwm.model.service;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmDimension;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luy.dwm.plan.bean.DpBusiProcess;

import java.util.List;

/**
 * <p>
 * ç»´åº¦ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
public interface DmDimensionService extends IService<DmDimension> {
    List<DmDimension> getQueryList(QueryInfo queryInfo);

    Integer getQueryTotal(QueryInfo queryInfo);
}
