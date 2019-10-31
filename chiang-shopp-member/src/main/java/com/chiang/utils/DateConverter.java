package com.chiang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 日期转换器
 * 
 * @author Administrator
 *
 */
public class DateConverter implements Converter<String, Date> {
	private final SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private final SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date convert(String source) {
		if (!StringUtils.isEmpty(source)) {
			try {
				return smf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) throws ParseException {
		String dateString = "2018-01-01 18:21:12";
		DateConverter dateC = new DateConverter();
		System.out.print(dateC.smf.parse(dateString));
	}

}
