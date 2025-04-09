package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmMetric;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.model.bean.DmModifier;
import com.luy.dwm.model.bean.DmTable;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
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
    
    @Select("select tc.table_id link_table_id, \n" +
            "       tc.id link_column_id, \n"+
            "       tc.column_name, \n"+
            "       tc.column_comment, \n"+
            "       t.model_id, \n"+
            "       t.domain_id, \n"+
            "       dm.* \n"+
            "from dm_table_column tc \n"+
            "join dm_table t on tc.table_id = t.id \n"+
            "left join dm_metric dm on tc.id = dm.link_column_id \n"+
            "where tc.table_id=#{tableId} and tc.is_deleted='0'")
    List<DmMetric> getQueryLinkList(Long tableId);

    //metricType='1301'代表原子指标，find_in_set是mysql的函数，用于判断一个值是否在一个集合中，返回1或0
    @Select("select distinct t.id,t.table_name from dm_metric dm join dm_table t on dm.link_table_id = t.id \n" +
            "where ( #{metricType} = '1301' and find_in_set(#{metricId},dm.link_atomic_metric_ids) )\n" +
            "   or ( #{metricType} = '1301' and dm.id = #{metricId})")
    List<DmTable> getMetricLinkTable(@Param("metricId") Long metricId,@Param("metricType") String metricType);
}
