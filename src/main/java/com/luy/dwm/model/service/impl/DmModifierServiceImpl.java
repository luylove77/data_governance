package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmModifier;
import com.luy.dwm.model.mapper.DmModifierMapper;
import com.luy.dwm.model.service.DmModifierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * é™�å®šè¯�è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Service
@DS("db1")
public class DmModifierServiceImpl extends ServiceImpl<DmModifierMapper, DmModifier> implements DmModifierService {

}
