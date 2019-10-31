package com.chiang.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chiang.bean.DateVo;

@RestController
@RequestMapping("/testDateConverter")
public class TestDateConverterImpl {
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	@ResponseBody
	@RequestMapping(value="/test",method= {RequestMethod.GET},produces= {"application/json; charset=UTF-8"})
	public DateVo getFormFront(Date date) {
		//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")仅用到返回类 参数注解
		//测试org.springframework.core.convert.converter.Converter
		DateVo dateVo = new DateVo();
		dateVo.setDate(date);
		return dateVo;
	}

}
