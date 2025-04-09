package com.luy.dwm.model.mapper;

import com.luy.dwm.model.bean.DmTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * æ•°æ�®è¡¨ Mapper 接口
 * </p>
 *
 * @author luyang
 * @since 2025-04-05
 */
@Mapper
@DS("db1")
public interface DmTableMapper extends BaseMapper<DmTable> {

    @Select("select t.*,dw.model_name,cc.code_name as table_status_name from dm_table t\n" +
            " left join dp_data_warehouse_model dw on t.model_id = dw.id\n" +
            " left join common_code cc on t.table_status = cc.code_no \n" +
            "where t.is_deleted = '0' ${condition}")
    List<DmTable> selectQueryList(String condition);

    @Select("select count(*) from dm_table t\n" +
            "where t.is_deleted = '0' ${condition}")
    Integer selectQueryCount(String condition);
}
