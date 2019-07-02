package com.boot.org.common;

/**
 * 泛型
 * @author Administrator
 *
 * @param <Key>
 * @param <Value>
 * @param <Prop>
 */
public class Base<Key,Value,Prop> {


	private Key selectKey;
	
	private Value putValue;
	
	private Prop propSetting;

	public Base(Key selectKey, Value putValue,Prop propSetting) {
		this.selectKey = selectKey;
		this.putValue = putValue;
		this.propSetting = propSetting;
	}
	
	public Base() {}

	public Key getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(Key selectKey) {
		this.selectKey = selectKey;
	}

	public Value getPutValue() {
		return putValue;
	}

	public void setPutValue(Value putValue) {
		this.putValue = putValue;
	}
	
	public Prop getPropSetting() {
		return propSetting;
	}

	public void setPropSetting(Prop propSetting) {
		this.propSetting = propSetting;
	}
}
