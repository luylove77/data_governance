package com.luy.dwm.plan.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.luy.dwm.plan.mapper.DpDataWarehouseModelMapper;
import com.luy.dwm.plan.service.DpDataWarehouseModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * æ•°ä»“æ¨¡åž‹ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-03-29
 */
@Service
@DS("db1")
public class DpDataWarehouseModelServiceImpl extends ServiceImpl<DpDataWarehouseModelMapper, DpDataWarehouseModel> implements DpDataWarehouseModelService {

}
