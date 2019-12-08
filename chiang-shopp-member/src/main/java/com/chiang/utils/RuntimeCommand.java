package com.chiang.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
/**
 * ExecutorService 
 * 线程运行超时限制
 * @author adp
 *
 */
public class RuntimeCommand {
	
	private static long shExecuteTime = 5*1000;
	public static ExecutorService es = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat("SHClient-%d").build());

	public static void main(String[] args) {
		//String cmd = "ipconfig";
		String cmd = "ping -t www.baidu.com";
		cmd.intern();
		Future<String> future = es.submit(new SHCallable<String>(cmd));
		String result = null;
		try {
			result = future.get(shExecuteTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	public static class SHCallable<V> implements Callable<V> {
		private String cmd;

		public SHCallable(String command) {
			this.cmd = command;
		}

		@Override
		public V call() throws Exception {
			Runtime runtime = Runtime.getRuntime();
			String rts = "success";
			// 执行命令返回约定值
			String mark = "success_";
			Process process = runtime.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = "";
			String runReturn = "";
			StringBuffer stringBuffer = new StringBuffer();
			while((line = br.readLine())!=null) {
			//if ((line = br.readLine()) != null) {
				System.out.println(line);
				stringBuffer.append(line+"\n");
			}
			if (!stringBuffer.toString().startsWith(mark)) {
				rts = "error";
			}
			runReturn = stringBuffer.toString();
			br.close();
			process.destroy();
			//return (V) rts;
			return (V) runReturn;
		}

	}
}
