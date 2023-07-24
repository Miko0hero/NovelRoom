package cn.deng.novel.codeGenerator;

/**
 * @author Deng
 * @date 2023/7/22
 * @description
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Generator {
    /**
     * 项目信息
     */
    //作者名称
    private static final String AUTHOR_NAME = "Deng";
    //获取用户目录
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    //代码生成路径
    private static final String JAVA_PATH = "/src/main/java";
    private static final String RESOURCE_PATH = "/src/main/resources";
    private static final String BASE_PACKAGE = "cn.deng.novel";

    /**
     * 数据库信息
     */
    private static final String DATABASE_IP = "127.0.0.1";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_NAME = "novel";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "123456";
    private static final String URL = String.format("jdbc:mysql://%s:%s/%s" +
            "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia" +
            "/Shanghai&allowPublicKeyRetrieval=true", DATABASE_IP, DATABASE_PORT, DATABASE_NAME);

    public static void main(String[] args) {
        // 传入需要生成的表名，多个用英文逗号分隔，所有用 all 表示
        genCode("all");

    }


    /**
     * 代码生成
     */
    private static void genCode(String tables) {

        // 全局配置
        FastAutoGenerator.create(URL, DATABASE_USERNAME, DATABASE_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(AUTHOR_NAME) // 设置作者
                            // kotlin
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy/MM/dd")
                            .dateType(DateType.ONLY_DATE)
                            .fileOverride()
                            .disableOpenDir()
                            .outputDir(PROJECT_PATH + JAVA_PATH); // 指定输出目录

                })
                // 包配置
                .packageConfig(builder -> builder.parent(BASE_PACKAGE) // 设置父包名
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .controller("controller.front")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_PATH + RESOURCE_PATH + "/mapper")))
                // 模版配置，取消自动生成的类别
                .templateConfig(builder -> builder.disable(TemplateType.SERVICE)
                        .disable(TemplateType.SERVICEIMPL)
                        .disable(TemplateType.CONTROLLER))
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(getTables(tables)) // 设置需要生成的表名
                        .controllerBuilder()
                        //开启生成@RestController 控制器
                        .enableRestStyle()
                        .mapperBuilder()
                        .enableMapperAnnotation()
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        //entity策略模式
                        .entityBuilder()
                        //开启Lombok注解
                        .enableLombok()
                        //.idType(ASSIGN_ID)
                        //设置逻辑删除的数据库列名
                        .logicDeleteColumnName("is_deleted")
                        //添加表字段填充(还需要配合数据填充器)，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                        .addTableFills(
                                new Column("create_time", FieldFill.INSERT),
                                new Column("update_time", FieldFill.INSERT_UPDATE)
                        )

                )
                // 开启生成@RestController 控制器
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板

                .execute();//执行

    }

    /**
     * 处理 all 和多表情况
     */
    protected static List<String> getTables(String tables) {
        //传入的为all时，返回一个空列表，则会默认生成所有的表
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}

