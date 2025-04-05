package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmDimension;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.plan.bean.DpBusiProcess;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ç»´åº¦ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Mapper
@DS("db1")
public interface DmDimensionMapper extends BaseMapper<DmDimension> {
    @Select("select t.*,dw.model_name from dm_dimension t\n" +
            " left join dp_data_warehouse_model dw on t.model_id = dw.id\n" +
            "where t.is_deleted = '0' ${condition}")
    List<DmDimension> selectQueryList(String condition);

    @Select("select count(*) from dm_dimension t\n" +
            "where t.is_deleted = '0' ${condition}")
    Integer selectQueryCount(String condition);
}
