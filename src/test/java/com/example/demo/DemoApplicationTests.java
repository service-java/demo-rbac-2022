package com.example.demo;

import java.util.ArrayList;

import com.example.demo.util.GenUtils;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.gen.SourceConfig;
import org.beetl.sql.gen.simple.EntitySourceBuilder;
import org.beetl.sql.gen.simple.MDSourceBuilder;
import org.beetl.sql.gen.simple.MapperSourceBuilder;
import org.beetl.sql.gen.simple.StringOnlyProject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	SQLManager sqlManager;
	String tableName = "user";

	@Test
	void contextLoads() {
	}

	@Test
	void gen() {

		// //为了简单起见，俩个sqlManager都来自同一个数据源，实际是不同数据库，甚至是NOSQL
		// this.sqlManager = DemoApplicationTests.init();
		// // GenUtils.initGroupTemplate();

		// GenUtils.genCode(sqlManager, tableName);
		// // this.genDoc();
		// // this.genAllDoc();

		List<SourceBuilder> sourceBuilder = new ArrayList<>();
		SourceBuilder entityBuilder = new EntitySourceBuilder();
		SourceBuilder mapperBuilder = new MapperSourceBuilder();
		SourceBuilder mdBuilder = new MDSourceBuilder();

		sourceBuilder.add(entityBuilder);
		sourceBuilder.add(mapperBuilder);
		sourceBuilder.add(mdBuilder);

		SourceConfig config = new SourceConfig(sqlManager, sourceBuilder);

		StringOnlyProject project = new StringOnlyProject();
		config.gen(tableName, project);
		String content = project.getContent();
	}

}
