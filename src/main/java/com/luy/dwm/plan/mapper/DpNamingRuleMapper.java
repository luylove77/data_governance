package com.luy.dwm.plan.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpNamingRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * å‘½å��è§„åˆ™ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-03-27
 */
@Mapper
@DS("db1")
public interface DpNamingRuleMapper extends BaseMapper<DpNamingRule> {

}
