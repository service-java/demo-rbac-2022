package com.example.demo.util;

import org.beetl.core.GroupTemplate;
import org.beetl.core.ReThrowConsoleErrorHandler;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.gen.SourceBuilder;
import org.beetl.sql.gen.SourceConfig;
import org.beetl.sql.gen.simple.*;

import java.util.ArrayList;
import java.util.List;

public class GenUtils {

    protected static void initGroupTemplate(){
        //指定模板文件路径，正常情况下，不需要要指定，默认在classpath:templates，但idea的环境读取不到
        GroupTemplate groupTemplate = BaseTemplateSourceBuilder.getGroupTemplate();
        String root = System.getProperty("user.dir");

        //代码模板在sql-gen，你可以指定自己的模板路径
        String templatePath = root+"/sql-gen/src/main/resources/templates/";
        FileResourceLoader  resourceLoader = new FileResourceLoader(templatePath);
        groupTemplate.setResourceLoader(resourceLoader);
    }

    /**
     * 代码生成，生成实体，mapper代码
     */
    public static void genCode(SQLManager sqlManager, String tableName){
        List<SourceBuilder> sourceBuilder = new ArrayList<>();
        SourceBuilder entityBuilder = new EntitySourceBuilder();
        SourceBuilder mapperBuilder = new MapperSourceBuilder();
        SourceBuilder mdBuilder = new MDSourceBuilder();

		sourceBuilder.add(entityBuilder);
		sourceBuilder.add(mapperBuilder);
		sourceBuilder.add(mdBuilder);

        SourceConfig config = new SourceConfig(sqlManager,sourceBuilder);

        config.setPreferDateType(SourceConfig.PreferDateType.LocalDate);
        
        // 如果有错误，抛出异常而不是继续运行1
        EntitySourceBuilder.getGroupTemplate().setErrorHandler(new ReThrowConsoleErrorHandler() );

        ConsoleOnlyProject project = new ConsoleOnlyProject();
        config.gen(tableName, project);
    }


    /**
     * 生成数据库文档
     */
    public static void genDoc(SQLManager sqlManager, String tableName){
        List<SourceBuilder> sourceBuilder = new ArrayList<>();
        SourceBuilder docBuilder = new MDDocBuilder();

        sourceBuilder.add(docBuilder);

        SourceConfig config = new SourceConfig(sqlManager,sourceBuilder);
        //如果有错误，抛出异常而不是继续运行1
        EntitySourceBuilder.getGroupTemplate().setErrorHandler(new ReThrowConsoleErrorHandler() );

        ConsoleOnlyProject project = new ConsoleOnlyProject();
        config.gen(tableName, project);
    }


    /**
     * 生成数据库文档
     */
    public static void genAllDoc(SQLManager sqlManager, String tableName){
        List<SourceBuilder> sourceBuilder = new ArrayList<>();
        SourceBuilder docBuilder = new MDDocBuilder();
        sourceBuilder.add(docBuilder);

        SourceConfig config = new SourceConfig(sqlManager, sourceBuilder);
        //如果有错误，抛出异常而不是继续运行1
        EntitySourceBuilder.getGroupTemplate().setErrorHandler(new ReThrowConsoleErrorHandler() );

        StringOnlyProject project = new StringOnlyProject();
        config.genAll(project);
        String output = project.getContent();
        System.out.println(output);
    }

}
