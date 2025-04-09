package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmTableSync;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@Mapper
@DS("db1")
public interface DmTableSyncMapper extends BaseMapper<DmTableSync> {

}
