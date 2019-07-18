package com.example.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;

import ch.qos.logback.core.util.FileUtil;

/**
 * 流程定义
 * 
 * @author 华为
 *
 */
@RestController
public class ProcessDefinition {

	@Autowired
	private RepositoryService repositotyService;

	@RequestMapping("/findDefine")
	public String findProcessDefine() {
		System.out.println("这里成功");
		List<org.activiti.engine.repository.ProcessDefinition> list = repositotyService.createProcessDefinitionQuery()// 创建流程定义查询
				// 等于添加where条件
				// .deploymentId(deploymentId);//部署id
				// .processDefinitionId(processDefinitionId)//流程定义id
				// .processDefinitionKey(processDefinitionKey)//流程定义key查询

				// 排序方式
				// .orderByDeploymentId()//通过部署id
				// .asc()//升序
				// .desc()//降序
				.orderByProcessDefinitionVersion().desc()
				// 返回的结果集
				.list();// 返回成集合
		// .singleResult();//返回单例对象
		// .listPage(firstResult, maxResults)//返回分页
		// .count();//返回数量

		for (org.activiti.engine.repository.ProcessDefinition pd : list) {
			System.out.println(pd.getId());
			System.out.println(pd.getName());
		}

		return "查询成功";
	}

	/**
	 * 删除流程定义
	 */
	@RequestMapping("/delProcessDefine")
	public String delProcessDefine(String deployMendId) {
		//只能删除未启动的流程，不能级联删除
		//repositotyService.deleteDeployment(deployMendId);
		
		//级联删除，可删除启动的流程
		repositotyService.deleteDeployment(deployMendId, true);
		System.out.println("删除成功");
		return "删除成功";
	}
	
	@RequestMapping("/showProcessImg")
	public String showProcessImg(String deployMentId) throws IOException {
		//获取图片资源名称
		List<String> list=repositotyService.getDeploymentResourceNames(deployMentId);
		String resourceName="";
		if(list != null && list.size()>0) {
			for(String name:list) {
				if(name.indexOf(".png")>0) {
					resourceName=name;
					System.out.println(name);
				}
			}
		}
		//获取图片资源
		InputStream input=repositotyService.getResourceAsStream(deployMentId, resourceName);
		//写出文件
		File file=new File("D:\\保存\\scwj\\"+resourceName);
		
		//判断文件是否存在
		if(file.getParentFile()!=null || !file.getParentFile().isDirectory()) {
			//不存在，进行创建
			file.getParentFile().mkdirs();
		}
		OutputStream output=null;
		try {
			output = new FileOutputStream(file);
			byte[] buf = new byte[512];
	        int bytesRead;
           while ((bytesRead = input.read(buf)) > 0) {
	               output.write(buf, 0, bytesRead);
	   }}finally {
			input.close();
			output.close();
		}

		return "查看流程图成功";
	}

	@RequestMapping("/findNewProcess")
	public String findNewProcess() {
		List<org.activiti.engine.repository.ProcessDefinition> list=repositotyService
				.createProcessDefinitionQuery()
						.orderByProcessDefinitionKey().asc().list();
		
		if(list != null && list.size()>0) {
			/**
			 * 使用map特性，key值相同时，后面的值会替换前面的值
			 */
			Map map=new HashMap<String, org.activiti.engine.repository.ProcessDefinition>();
			for(org.activiti.engine.repository.ProcessDefinition pd:list) {
				map.put(pd.getKey(), pd);
			}
			List<org.activiti.engine.repository.ProcessDefinition> list1 
			=new ArrayList(map.values());
			for(org.activiti.engine.repository.ProcessDefinition pd:list1) {
				System.out.println(pd.getId());
				System.out.println(pd.getName());
			}
		
		
		}
		return "查询最新流程定义版本成功";
	}

	
	/**
	 * 通过流程定义key删除所有的流程
	 * @param processKey
	 * @return
	 */
	@RequestMapping("/delAllProcess")
	public String delAllProcess(String processKey) {
		List<org.activiti.engine.repository.ProcessDefinition> list=repositotyService
				.createProcessDefinitionQuery()
						.processDefinitionKey(processKey)
						.list();
		
		for (org.activiti.engine.repository.ProcessDefinition processDefinition : list) {
			String id=processDefinition.getDeploymentId();
			repositotyService.deleteDeployment(id, true);
			System.out.println("删除："+processDefinition.getName()+",版本："+
			processDefinition.getVersion());
		}
		
		
		
		return "删除所有流程定义";
	}
	
}
