package com.luy.dwm.plan.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * æ•°æ�®åŸŸ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-03-31
 */
@Mapper
@DS("db1")
public interface DpDataDomainMapper extends BaseMapper<DpDataDomain> {

}
