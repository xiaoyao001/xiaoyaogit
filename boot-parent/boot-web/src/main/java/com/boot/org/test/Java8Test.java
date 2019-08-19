package com.boot.org.test;

public class Java8Test {
	
	static GreetingService service = (String message,String code) -> {
		Integer number = Integer.parseInt(code);
		return message+code;
	};
	
	public static void main(String[] args) {
		System.out.println(service.showMsg("abc", "123"));
	}
	
	interface GreetingService{
		String showMsg(String message,String code);
	}
}
