package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmTableColumn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * å­—æ®µè¡¨ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Mapper
@DS("db1")
public interface DmTableColumnMapper extends BaseMapper<DmTableColumn> {

}
