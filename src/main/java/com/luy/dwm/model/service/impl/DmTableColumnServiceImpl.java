package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmTableColumn;
import com.luy.dwm.model.mapper.DmTableColumnMapper;
import com.luy.dwm.model.service.DmTableColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * å­—æ®µè¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Service
@DS("db1")
public class DmTableColumnServiceImpl extends ServiceImpl<DmTableColumnMapper, DmTableColumn> implements DmTableColumnService {

}
