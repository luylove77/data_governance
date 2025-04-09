package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmTableSync;
import com.luy.dwm.model.mapper.DmTableSyncMapper;
import com.luy.dwm.model.service.DmTableSyncService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * æ•°æ�®å�Œæ­¥ä¿¡æ�¯è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@Service
@DS("db1")
public class DmTableSyncServiceImpl extends ServiceImpl<DmTableSyncMapper, DmTableSync> implements DmTableSyncService {

}
