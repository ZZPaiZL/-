package com.example.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringActivityApplicationTests {

	/**
	 * 使用工作流引擎对象创建数据库所需的23张表
	 */
	@Test
	public void createTableActivity() {
		ProcessEngineConfiguration processEngine =	ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		processEngine.setJdbcDriver("org.postgresql.Driver");
		processEngine.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/bpm_activity");
		processEngine.setJdbcUsername("postgres");
		processEngine.setJdbcPassword("Zz123456");
		
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE
		//设置创建表的方式为:当出现true时，自动创建，false时不自动创建
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP
		//先删除表，在创建表
		processEngine.setDatabaseSchemaUpdate(
				ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);
		//启动流程引擎对象
		ProcessEngine process=processEngine.buildProcessEngine();
		System.out.println("processEngine:"+process);
	}

}
