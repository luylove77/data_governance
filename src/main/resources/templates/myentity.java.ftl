package ${package.Entity};

import lombok.Data;
import java.io.Serializable;

/**
 * ${table.comment}
 *
 * @author ${author}
 * @since ${date}
 */
@Data
public class ${entity}  {

<#list table.fields as field>
    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};
</#list>

}