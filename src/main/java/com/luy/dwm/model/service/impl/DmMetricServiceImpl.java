package com.luy.dwm.model.service.impl;

import com.luy.dwm.common.bean.QueryInfo;
import com.luy.dwm.common.util.SqlUtil;
import com.luy.dwm.model.bean.DmMetric;
import com.luy.dwm.model.bean.DmModifier;
import com.luy.dwm.model.bean.DmTable;
import com.luy.dwm.model.mapper.DmMetricMapper;
import com.luy.dwm.model.service.DmMetricService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * æŒ‡æ ‡ 服务实现类
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Service
@DS("db1")
public class DmMetricServiceImpl extends ServiceImpl<DmMetricMapper, DmMetric> implements DmMetricService {
    public List<DmMetric> getQueryList(QueryInfo queryInfo){
        String condition= "";
        if(queryInfo.getModelId() !=null){
            condition +=" and t.model_id="+queryInfo.getModelId();
        }

        if(queryInfo.getMetricName()!=null&&queryInfo.getMetricName().trim().length()>0){
            condition+="and t.metric_name like '%"+ SqlUtil.filterUnsafeSql(queryInfo.getMetricName()) +"%' ";
        }
        condition += queryInfo.getLimitSQL();

        List<DmMetric> list = this.baseMapper.selectQueryList(condition);
        return list;
    }

    @Override
    public Integer getQueryTotal(QueryInfo queryInfo){
        String condition= "";
        if(queryInfo.getModelId() !=null){
            condition +=" and t.model_id="+queryInfo.getModelId();
        }
        Integer total=  this.baseMapper.selectQueryCount(condition);
        return total;
    }

    @Override
    public Object getQueryLinkList(Long tableId) {
        List<DmMetric> list = this.baseMapper.getQueryLinkList(tableId);
        return list;
    }

    @Override
    public List<DmTable> getMetricLinkTable(Long metricId) {
        //查询指标Id 关联的tableId tableName
        DmMetric dmMetric = this.getById(metricId);

        List<DmTable> dmTableList = this.baseMapper.getMetricLinkTable(metricId,dmMetric.getMetricType());

        return dmTableList;
    }
}
