/**
 * @author chiang
 *
 */
package com.chiang.schedule.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	/*@Scheduled(fixedRate=1000)
	public void showCurrentTime() {
		System.out.println("时间为"+dateFormat.format(new Date()));
	}*/
}