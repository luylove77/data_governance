package com.luy.dwm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class CodeGen3531 {

    public static void main(String[] args) {

        String[] tables={  "dm_modifier"  };

        FastAutoGenerator.create("jdbc:mysql://hadoop100:3306/dwm","root","root")
                .globalConfig(builder -> {
                    builder.author("luyang")               //作者
                            .outputDir("F:\\code\\bigdata\\data_governance\\src\\main\\java")
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE);  //选择实体类中的日期类型  ，Date or LocalDatetime

                })
                .packageConfig(builder -> {                 //各个package 名称
                    builder.parent("com.luy.dwm")
                            .moduleName("model")
                            .entity("bean")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            ;

                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .serviceBuilder()
                            .formatServiceFileName("%sService")  //类后缀
                            .formatServiceImplFileName("%sServiceImpl")  //类后缀
                            .entityBuilder()
                            .enableLombok()  //允许使用lombok
                            .controllerBuilder()
                            .formatFileName("%sController")  //类后缀
                            .enableRestStyle()   //生成@RestController 否则是@Controller
                            .mapperBuilder()
                            //生成通用的resultMap 的xml映射
                            .enableBaseResultMap()  //生成xml映射
                            .superClass(BaseMapper.class)  //标配
                            .formatMapperFileName("%sMapper")  //类后缀
                            .enableFileOverride()   //生成代码覆盖已有文件 谨慎开启
                            .mapperAnnotation(Mapper.class) ; //生成代码Mapper上自带@Mapper

                })
                .templateConfig(new Consumer<TemplateConfig.Builder>() {
                    @Override
                    public void accept(TemplateConfig.Builder builder) {
                        // 实体类使用我们自定义模板
                        builder.entity("templates/myentity.java");
                        builder.mapper("templates/mymapper.java");
                        builder.serviceImpl("templates/myserviceimpl.java");
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
