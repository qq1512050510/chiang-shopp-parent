/**
 * 
 */
/**
 * @author adp
 *
 */
package com.chiang.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.chiang.quartz.service.JobService;

/**
 * @author 
 * createTime 
 **/
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
	@Autowired
	private JobService jobService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动后打印参数");
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        System.out.println("---");
        //jobService.runJobTimes();
        System.out.println("---");
    }
}