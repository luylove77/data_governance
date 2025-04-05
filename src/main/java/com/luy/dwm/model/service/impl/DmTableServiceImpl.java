package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.mapper.DmTableMapper;
import com.luy.dwm.model.service.DmTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * æ•°æ�®è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Service
@DS("db1")
public class DmTableServiceImpl extends ServiceImpl<DmTableMapper, DmTable> implements DmTableService {

}
