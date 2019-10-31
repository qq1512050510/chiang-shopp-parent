package com.chiang.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class DateVo {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;
}
