package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmMetric;
import com.luy.dwm.model.mapper.DmMetricMapper;
import com.luy.dwm.model.service.DmMetricService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * æŒ‡æ ‡ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Service
@DS("db1")
public class DmMetricServiceImpl extends ServiceImpl<DmMetricMapper, DmMetric> implements DmMetricService {

}
