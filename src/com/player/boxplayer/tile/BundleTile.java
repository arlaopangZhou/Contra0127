package com.player.boxplayer.tile;
/**
 * 解析bundle字段的bean。
 * @author richardzhou
 *
 */
public class BundleTile {

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getBundleKey() {
		return bundleKey;
	}
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}

	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	private String valueType;//bundle的值的类型名称
	private String bundleKey;//bundle的键名称
	private String value;//bundle的值名称
}
