package com.luy.dwm.plan.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpDataWarehouseModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * æ•°ä»“æ¨¡åž‹ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-03-29
 */
@Mapper
@DS("db1")
public interface DpDataWarehouseModelMapper extends BaseMapper<DpDataWarehouseModel> {

}
