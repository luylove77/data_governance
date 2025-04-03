package com.luy.dwm.plan.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.plan.bean.DpBusiProcess;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.luy.dwm.plan.mapper.DpBusiProcessMapper;
import com.luy.dwm.plan.service.DpBusiProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ä¸šåŠ¡è¿‡ç¨‹ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-02
 */
@Service
@DS("db1")
public class DpBusiProcessServiceImpl extends ServiceImpl<DpBusiProcessMapper, DpBusiProcess> implements DpBusiProcessService {

    @Override
    public List<DpBusiProcess> getQueryList(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }
        condition+= queryInfo.getLimitSQL();

        List<DpBusiProcess> dpBusiProcess = this.baseMapper.selectBusiProcessList(condition);


        return dpBusiProcess;
    }

    @Override
    public Integer getQueryTotal(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }

        Integer total = this.baseMapper.selectBusiProcessCount(condition);

        return total;
    }
}
