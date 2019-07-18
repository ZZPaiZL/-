package com.example.demo.controller;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启流程
 * @author 华为
 *
 */
@RestController
public class StartProcess {
	//流程引擎
	@Autowired
	private ProcessEngine processEngine ;
	
	//仓库服务
	 @Autowired
	 private RepositoryService repositoryService;
	 
	 //运行服务
	 @Autowired
	 private RuntimeService runtimeService;
	 
	 @Autowired
	 private TaskService taskService;
	 
	/**
	 * 1.部署流程定义
	 */
	@RequestMapping("/startProcessDefinde")
	public String startProcessDefinde() {
		//一种方式
		Deployment deloyment = repositoryService.createDeployment()
		.addClasspathResource("processes/activity.bpmn")//从资源文件下加载数据
		.addClasspathResource("processes/activity.png")
		.name("火之国国访")//添加部署名字或者流程名字
		.deploy();
		
		
		//第二种方式
//		Deployment deloyment = processEngine
//						.getRepositoryService()//获取与流程定义和部署对象相关的service
//						.createDeployment()//创建一个部署对象
//						.addClasspathResource("processes/activity.bpmn")//从资源文件下加载数据
//						.addClasspathResource("processes/activity.png")
//						.name("helloword")//添加部署名字或者流程名字
//						.deploy();
		
		System.out.println("部署id:"+deloyment.getId());//部署id
		System.out.println("部署名字: "+deloyment.getName());//部署名字
		return "流程定义成功";
	}
	
	/**
	 * 开始启动流程
	 */
	@RequestMapping("/startProcess")
	public String startProcess() {
		
		String key="helloworld";
		ProcessInstance pi=runtimeService
			.startProcessInstanceByKey(key);
			//使用流程定义的key启动流程实例，key对应的*.bpmn中的id
			//使用key启动，可以找到最新版本进行启动
			System.out.println("流程实例id:"+pi.getId());//流程实例id
			System.out.println("流程实例名字:"+pi.getName());//流程实例名字
			System.out.println("流程定义id:"+pi.getProcessDefinitionId());//流程定义id
			
			
			//processEngine.getRuntimeService().startProcessInstanceByKey(key);
			
			
		return "流程申请成功";
	}
	
	
	/**
	 * 查询当前的个人任务
	 */
	@RequestMapping("/findProcessTask")
	public String findProcessTask(String name) {
		String assignee=name;
		List<Task> list=taskService.createTaskQuery()//创建查询正在执行任务对象
		.taskAssignee(assignee)//指定办理人名称
		.list();//获取集合
		
		if(list != null && list.size()>0) {
			for(Task task : list) {
				System.out.println("任务id:"+task.getId());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务办理人："+task.getAssignee());
				System.out.println("任务描述："+task.getDescription());
				System.out.println("流程实例id："+task.getProcessInstanceId());
			}
		}
		
		return "查询成功";
	}
	
	/**
	 * 完成任务
	 */
	@RequestMapping("/completeTask")
	public String completeTask(String id) {
		String taskId = id;//任务id
		taskService.complete(taskId);//办理完成
		System.out.println("任务id:"+taskId+"办理完成");
		
		return "办理完成";
	}
	
	
	
	
	
}
