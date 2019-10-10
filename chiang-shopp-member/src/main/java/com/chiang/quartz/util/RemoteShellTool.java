package com.chiang.quartz.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

public class RemoteShellTool {

	private static final Logger log = LoggerFactory.getLogger(RemoteShellTool.class);

	private Connection conn;
	private Session session;
	private SCPClient scpClient;

	// @Value("${msg.text}")
	private String ipAddr;
	private String charset = Charset.defaultCharset().toString();
	// @Value("${msg.text}")
	private String userName;
	// @Value("${msg.text}")
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
		// 1、新建连接
		conn = new Connection(ipAddr);
		conn.connect();
		// 2、登录授权
		return conn.authenticateWithPassword(userName, password);
	}

	/**
	 * 
	 * @param cmds
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> exec(String cmds) throws IOException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		InputStream in = null;
		String result = "";
		boolean flag = false;
		log.info("启动ssh");
		if (this.login()) {
			log.info("执行命令{}", cmds);
			// 3、获取session
			session = conn.openSession();
			// 4、执行命令
			session.execCommand(cmds);
			in = session.getStdout();
			result = this.processStdout(in, this.charset);
			log.info("返回结果：{}", result);
			// this.close();
			flag = true;
			returnMap.put("result", result);
			returnMap.put("flag", flag);

		}
		return returnMap;
	}

	public void close() {
		// 5、释放session
		session.close();
		// 6、释放连接
		conn.close();
	}

	public String processStdout(InputStream in, String charset) {
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try {
			while (in.read(buf) != -1) {
				sb.append(new String(buf, charset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param remoteFile
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean getFile(String remoteFile, String path) {
		// 1、登录
		try {
			if (this.login()) {
				// 2、SCPClient
				scpClient = conn.createSCPClient();
				// 3、拿文件
				scpClient.get(remoteFile, path);
				log.info("远程下载文件：{}，到本地：{}", remoteFile, path);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean putFile(String localFile, String remoteTargetDirectory) {
		// 1、登录
		try {
			if (this.login()) {
				// 2、获取scpClient
				scpClient = conn.createSCPClient();
				// 3、上传文件
				scpClient.put(localFile, remoteTargetDirectory);
				log.info("上传文件：{}，到远程：{}", localFile, remoteTargetDirectory);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		RemoteShellTool tool = new RemoteShellTool("192.168.111.129", "root", "root", "utf-8");
		String cmds = "wget http://192.168.43.95:8762/testMember/test";
		String cmd_findFile = "find . -name 'test.*'";
		// boolean exec = tool.exec("sh test.sh");
		boolean exec = (boolean) tool.exec(cmds).get("flag");
		System.out.println("----");
		System.out.println(exec);
		String result = (String) tool.exec(cmd_findFile).get("result");
		System.out.println("----");
		tool.putFile("C:\\Users\\adp\\Desktop\\资料.txt", "/root");
		tool.getFile("/root/test123.do", "C:\\Users\\adp\\Desktop\\");
		System.out.println("");
		System.out.println(result);
		if (StringUtils.isEmpty(result)) {
			System.out.println("未下载到指定文件");
		} else {
			System.out.println("下载到指定文件");
		}
	}

}
