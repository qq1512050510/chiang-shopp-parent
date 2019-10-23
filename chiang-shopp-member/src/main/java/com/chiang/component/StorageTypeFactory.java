package com.chiang.component;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chiang.exception.UnknownStorageTypeException;
import com.chiang.quartz.service.BaseService;

/**
 * 获取存储类型的工厂类
 * 
 * @author Administrator
 *
 */
@Component
public class StorageTypeFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private static Map<String, BaseService> storageTypeEnumFileServiceMap;

	private static Logger log = LoggerFactory.getLogger(StorageTypeFactory.class);

	/**
	 * 项目启动时执行
	 */
	@Override
	public void setApplicationContext(ApplicationContext appCT) throws BeansException {
		applicationContext = appCT;
		//ApplicationContext ctx=new ClassPathXmlApplicationContext("com/herman/ss/config/applicationContext2.xml");
		// 获取 Spring容器中所有 BaseService类型的类
		storageTypeEnumFileServiceMap = appCT.getBeansOfType(BaseService.class);
		log.info("启动获取的BaseService的Map{}", storageTypeEnumFileServiceMap);
	}

	/**
	 * 获取指定存储类型的 Service
	 * 
	 * @throws UnknownStorageTypeException
	 */
	public BaseService getStorageTypeService(String type) {
		BaseService result = null;
		for (BaseService baseService : storageTypeEnumFileServiceMap.values()) {
			if (baseService.getStorageType().equals(type)) {
				result = baseService;
				break;
			}
		}
		if (result == null) {
			throw new UnknownStorageTypeException("未发现" + type + "该类型的异常");
		}
		return result;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	

}
