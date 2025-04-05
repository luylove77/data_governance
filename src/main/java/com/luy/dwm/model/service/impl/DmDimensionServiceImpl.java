package com.luy.dwm.model.service.impl;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.model.bean.DmDimension;
import com.luy.dwm.model.mapper.DmDimensionMapper;
import com.luy.dwm.model.service.DmDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luy.dwm.plan.bean.DpBusiProcess;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

/**
 * <p>
 * ç»´åº¦ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Service
@DS("db1")
public class DmDimensionServiceImpl extends ServiceImpl<DmDimensionMapper, DmDimension> implements DmDimensionService {

    @Override
    public List<DmDimension> getQueryList(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }
        condition+= queryInfo.getLimitSQL();

        List<DmDimension> list = this.baseMapper.selectQueryList(condition);


        return list;
    }

    @Override
    public Integer getQueryTotal(QueryInfo queryInfo) {
        String condition = "";
        if(queryInfo.getModelId() != null){
            condition = "and t.model_id" + queryInfo.getModelId();
        }

        Integer total = this.baseMapper.selectQueryCount(condition);

        return total;
    }
}
