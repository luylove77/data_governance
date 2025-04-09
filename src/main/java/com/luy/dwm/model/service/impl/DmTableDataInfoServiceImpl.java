package com.luy.dwm.model.service.impl;

import com.luy.dwm.model.bean.DmTableDataInfo;
import com.luy.dwm.model.mapper.DmTableDataInfoMapper;
import com.luy.dwm.model.service.DmTableDataInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * æ•°æ�®ä¿¡æ�¯è¡¨ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-09
 */
@Service
@DS("db1")
public class DmTableDataInfoServiceImpl extends ServiceImpl<DmTableDataInfoMapper, DmTableDataInfo> implements DmTableDataInfoService {

}
