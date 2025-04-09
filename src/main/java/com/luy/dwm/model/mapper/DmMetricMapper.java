package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmMetric;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.model.bean.DmModifier;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * æŒ‡æ ‡ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Mapper
@DS("db1")
public interface DmMetricMapper extends BaseMapper<DmMetric> {
    @Select("select t.*,dw.model_name,cc.code_name as metric_type_name,dd.name_chn as domain_name from dm_metric t \n" +
            " left join dp_data_warehouse_model dw on t.model_id =dw.id \n" +
            " left join dp_data_domain dd on t.domain_id =dd.id \n" +
            " left join common_code cc on t.metric_type =cc.code_no \n" +
            " where t.is_deleted='0' ${condition}")
    List<DmMetric> selectQueryList(String condition);

    @Select("select count(*) from dm_metric t \n" +
            " where t.is_deleted='0' ${condition}")
    Integer selectQueryCount(String condition );

}
