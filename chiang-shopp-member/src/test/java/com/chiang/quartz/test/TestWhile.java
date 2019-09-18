package com.chiang.quartz.test;

public class TestWhile {
	public static void main(String[]args) {
		int i = 0;
		while(true) {
			i++;
			if(i==100) {
				continue;
			}
			if(i==120) {
				continue;
			}
			
			if(i==300) {
				return;
			}
			System.out.println(i);
		}
	}
}
