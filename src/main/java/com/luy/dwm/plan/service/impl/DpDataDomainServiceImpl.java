package com.luy.dwm.plan.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.luy.dwm.plan.mapper.DpDataDomainMapper;
import com.luy.dwm.plan.service.DpDataDomainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * æ•°æ�®åŸŸ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-03-31
 */
@Service
@DS("db1")
public class DpDataDomainServiceImpl extends ServiceImpl<DpDataDomainMapper, DpDataDomain> implements DpDataDomainService {
    public List<DpDataDomain> getDomainList(Long modelId){
        String condition = "";
        if(modelId != null){
            condition = "and t.model_id = "+modelId;
        }

        List<DpDataDomain> dpDataDomains = this.baseMapper.selectDomainList(condition);
        return dpDataDomains;

    }
}
