package com.luy.dwm.plan.service;

import com.luy.dwm.plan.bean.DpDataDomain;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * æ•°æ�®åŸŸ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-03-31
 */
public interface DpDataDomainService extends IService<DpDataDomain> {
    public List<DpDataDomain> getDomainList(Long modelId);
}
