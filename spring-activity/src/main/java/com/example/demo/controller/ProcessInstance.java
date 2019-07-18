package com.example.demo.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程实例对象
 * @author 华为
 *
 */
@RestController
public class ProcessInstance {
		
	//流程引擎
	@Autowired
	private ProcessEngine processEngine ;
	/**
	 * 1.部署流程定义
	 */
	@RequestMapping("/ProcessDefindeStart")
	public String startProcessDefinde() {
		//一种方式
		Deployment deloyment = processEngine.getRepositoryService()
				.createDeployment()
		.addClasspathResource("processes/activity.bpmn")//从资源文件下加载数据
		.addClasspathResource("processes/activity.png")
		.name("火之国国访")//添加部署名字或者流程名字
		.deploy();
		
		System.out.println("部署id:"+deloyment.getId());//部署id
		System.out.println("部署名字: "+deloyment.getName());//部署名字
		return "流程定义成功";
	}
	
	
	
	
}
