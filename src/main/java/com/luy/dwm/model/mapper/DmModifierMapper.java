package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmModifier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * é™�å®šè¯�è¡¨ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Mapper
@DS("db1")
public interface DmModifierMapper extends BaseMapper<DmModifier> {

}
