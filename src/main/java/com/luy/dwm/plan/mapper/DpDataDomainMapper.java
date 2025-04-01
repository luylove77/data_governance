package com.luy.dwm.plan.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpDataDomain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author luyang
 * @since 2025-03-31
 */
@Mapper
@DS("db1")
public interface DpDataDomainMapper extends BaseMapper<DpDataDomain> {
    @Select("select t.*,dw.model_name from dp_data_domain t\n" +
            " left join dp_data_warehouse_model dw on t.model_id = dw.id\n" +
            "where t.is_deleted = '0' ${condition}")
    public List<DpDataDomain> selectDomainList(String condition);
}
