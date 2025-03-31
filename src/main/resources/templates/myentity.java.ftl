package ${package.Entity};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * ${table.comment}
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@TableName("${table.name}")
public class ${entity} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list table.fields as field>


    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};
</#list>

}