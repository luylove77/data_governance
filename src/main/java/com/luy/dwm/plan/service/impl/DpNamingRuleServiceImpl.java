package com.luy.dwm.plan.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpNamingRule;
import com.luy.dwm.plan.mapper.DpNamingRuleMapper;
import com.luy.dwm.plan.service.DpNamingRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * å‘½å��è§„åˆ™ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-03-27
 */
@Service
@DS("db1")
public class DpNamingRuleServiceImpl extends ServiceImpl<DpNamingRuleMapper, DpNamingRule> implements DpNamingRuleService {

}
