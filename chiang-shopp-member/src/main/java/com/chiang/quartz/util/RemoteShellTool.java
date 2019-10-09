package com.chiang.quartz.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class RemoteShellTool {
	
	private static final Logger log = LoggerFactory.getLogger(RemoteShellTool.class);

	private Connection conn;
	private String ipAddr;
	private String charset = Charset.defaultCharset().toString();
	private String userName;
	private String password;

	/**
	 * 
	 */
	public RemoteShellTool(String ipAddr, String userName, String password, String charset) {
		this.ipAddr = ipAddr;
		this.userName = userName;
		this.password = password;
		if (!StringUtils.isEmpty(charset)) {
			this.charset = charset;
		}
	}

	/**
	 * ssh 登录
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean login() throws IOException {
		//1、新建连接
		conn = new Connection(ipAddr);
		conn.connect();
		//2、登录授权
		return conn.authenticateWithPassword(userName, password);
	}

	/**
	 * 
	 * @param cmds
	 * @return
	 */
	public boolean exec(String cmds) {
		InputStream in = null;
		String result = "";
		boolean flag = false;
		try {
			log.info("启动ssh");
			if (this.login()) {
				log.info("执行命令{}", cmds);
				//3、获取session
				Session session = conn.openSession();
				//4、执行命令
				session.execCommand(cmds);
				in = session.getStdout();
				result = this.processStdout(in, this.charset);
				log.info("返回结果：{}",result);
				//5、释放session
				session.close();
				//6、释放连接
				conn.close();
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String processStdout(InputStream in, String charset) {
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while(in.read(buf)!=-1) {
				sb.append(new String(buf,charset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		RemoteShellTool tool = new RemoteShellTool("192.168.111.129","root","root","utf-8");
		boolean exec = tool.exec("sh test.sh");
		System.out.println(exec);
	}

}
