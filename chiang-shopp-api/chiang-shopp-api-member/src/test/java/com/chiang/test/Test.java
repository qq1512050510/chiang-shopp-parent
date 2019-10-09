package com.chiang.test;

import java.io.IOException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {
		//char s = (char) System.in.read();
		Scanner scan = new Scanner(System.in);
		int i = scan.nextInt();
		int j = scan.nextInt();
		System.out.println(i+j);
		//System.out.println(s);
		System.out.println(-Math.round(2.4));
	}

}
