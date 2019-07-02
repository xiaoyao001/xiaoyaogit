package com.boot.org.common;

import java.util.Map;

public class Student extends Base<String, Map<String, Object>, Integer>{

	private Integer id;
	
	private String name;
	
	private boolean flag;
	
	public Student(String selectKey, Map<String, Object> putValue, Integer propSetting, Integer id, String name,
			boolean flag) {
		super(selectKey, putValue, propSetting);
		this.id = id;
		this.name = name;
		this.flag = flag;
	}

	public Student() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public static void main(String[] args) {

		
	}
}
