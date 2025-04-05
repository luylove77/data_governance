package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.mapper.DmDimensionMapper;
import com.luy.dwm.model.service.DmDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * ç»´åº¦ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Service
@DS("db1")
public class DmDimensionServiceImpl extends ServiceImpl<DmDimensionMapper, DmDimension> implements DmDimensionService {

}
