package com.chiang.annotation.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chiang.annonation.UseCase;

public class UseCaseTracker {
	
	public static void trackUseCases(List<Integer> useCases, Class<?> cl) {

		for (Method m : cl.getDeclaredMethods()) {
			UseCase uc = m.getAnnotation(UseCase.class);
			if (null != uc) {
				System.out.println("Cound Use Case:" + uc.id() + " " + uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}
		for (int i : useCases) {
			System.out.println("Warning: Missing use case-" + i);
		}

	}

	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUseCases(useCases, PasswordUtils.class);
	}
	/**
	 * 
	 * output: 
	 * Cound Use Case:49 New passwords can't equal previously used ones
	 * Cound Use Case:47 Passwords must contain at least one numeric Cound Use
	 * Case:48 no description Warning: Missing use case-50
	 * 
	 */

}
