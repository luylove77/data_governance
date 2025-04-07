package com.luy.dwm.model.service;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * æ•°æ�®è¡¨ 服务类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
public interface DmTableService extends IService<DmTable> {
    void saveTableAll(DmTable dmTable);

    List<DmTable> getQueryList(QueryInfo queryInfo);

    Integer getQueryTotal(QueryInfo queryInfo);

    DmTable getTableAll(Long id);
}
