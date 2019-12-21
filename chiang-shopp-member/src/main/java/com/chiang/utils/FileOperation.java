package com.chiang.utils;

import java.io.File;

import org.aspectj.util.FileUtil;

public class FileOperation {

	public static void main(String[] args) {
		String fileName = "test.txt";
		String fileDir = "d:"+File.separator;
		File file = new File(fileDir+fileName);
		System.out.println(fileDir+fileName);
		String contents = "Hello World!!!";
		FileUtil.writeAsString(file, contents);
	}

}
