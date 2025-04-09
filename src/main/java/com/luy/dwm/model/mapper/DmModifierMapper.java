package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmModifier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luy.dwm.plan.bean.DpBusiProcess;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * é™�å®šè¯�è¡¨ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-08
 */
@Mapper
@DS("db1")
public interface DmModifierMapper extends BaseMapper<DmModifier> {

    @Select("select t.*,dw.model_name \n" +
            " from  dm_modifier t \n" +
            " left join dp_data_warehouse_model dw \n" +
            " on t.model_id =dw.id \n" +
            " where t.is_deleted='0' ${condition}")
    List<DmModifier> selectQueryList(String condition);

    @Select("select count(*) from dm_modifier t \n" +
            " where t.is_deleted='0' ${condition}")
    Integer selectQueryCount(String condition );

}
