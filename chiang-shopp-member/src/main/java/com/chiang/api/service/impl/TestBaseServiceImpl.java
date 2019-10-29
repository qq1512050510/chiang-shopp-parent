package com.chiang.api.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.component.StorageTypeFactory;
import com.chiang.constant.Constants;
import com.chiang.quartz.service.BaseService;

@RestController
@RequestMapping("/testBaseService")
/*@Controller*/
public class TestBaseServiceImpl {

	@Autowired
	@Qualifier(Constants.BASE_SERVICE1)
	private BaseService baseService1;

	@Resource(name = Constants.BASE_SERVICE2)
	private BaseService baseService2;
	
	@Autowired
	private StorageTypeFactory storageTypeFactory; 
	
	
	private String storageType = Constants.BASE_SERVICE1;
	
	

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	/**
	 * initStorageType 接口根据storageType自动注入BaseService
	 */
	public BaseService baseService3;

	@RequestMapping(value = "/testApi1", method = { RequestMethod.GET })
	public String testApi1() {
		baseService1.method();
		return baseService1.getStorageType();
	}

	@RequestMapping(value = "/testApi2", method = { RequestMethod.GET })
	public String testApi2() {
		baseService1.method();
		return baseService2.getStorageType();
	}

	@RequestMapping(value = "/testApi3", method = { RequestMethod.GET })
	public String testApi3() {
		baseService3.method();
		return baseService3.getStorageType();
	}

	/**
	 * 
	 * @param storageType
	 */
	// @PostConstruct 注解，表示该类初始化的时候，自动调用该方法
	@PostConstruct
	public String initStorageType() {
		/*
		 * @RequestParam(value="storageType",defaultValue=Constants.BASE_SERVICE1,
		 * required=false)
		 * 
		 * @RequestParam(value = "storageType", defaultValue = "")String storageType
		 */
		// 如果storageType为空，则表示是启动时初始化，有值说明是Web接口动态更改的
		

		// 添加defaultValue后就不需要下面的非空赋值了
		if (StringUtils.isEmpty(storageType)) {
			storageType = Constants.BASE_SERVICE1;
		}

		baseService3 = storageTypeFactory.getStorageTypeService(storageType);
		/**
		 * 之前有问题的代码
		 * baseService3 = StorageTypeFactory.getStorageTypeService(storageType);
		 * 直接使用静态方法
		 */
		if (ObjectUtils.isEmpty(baseService3)) {
			return "error";
		}
		return "success";
	}
	
	@GetMapping(value = "/updateStorageType")
	public String updateStorageType(String type) {
		setStorageType(type);
		initStorageType();
		return type;
	}

}
