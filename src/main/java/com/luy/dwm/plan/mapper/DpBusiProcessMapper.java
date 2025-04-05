package com.luy.dwm.plan.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luy.dwm.plan.bean.DpBusiProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.plan.bean.DpDataDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ä¸šåŠ¡è¿‡ç¨‹ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-02
 */
@Mapper
@DS("db1")
public interface DpBusiProcessMapper extends BaseMapper<DpBusiProcess> {

    @Select("select t.*,dw.model_name,dd.name_chn as domain_name from dp_busi_process t\n" +
            " left join dp_data_warehouse_model dw on t.model_id = dw.id\n" +
            " left join dp_data_domain dd on t.domain_id = dd.id\n" +
            "where t.is_deleted = '0' ${condition}")
    List<DpBusiProcess> selectBusiProcessList(String condition);

    @Select("select count(*) from dp_busi_process t\n" +
            "where t.is_deleted = '0' ${condition}")
    Integer selectBusiProcessCount(String condition);
}
