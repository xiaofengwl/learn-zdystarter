package com.zdy.mystarter.basic.swaggers.service;

import com.zdy.mystarter.basic.swaggers.constants.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务响应对象封装
 */
public class ServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	// 返回代码
	private String ec;
	// 错误消息
	private String em;
	private Map<String, Object> data = new HashMap<>();

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public String getEm() {
		return em;
	}

	public void setEm(String em) {
		this.em = em;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Object getData(String key) {
		return data.get(key);
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setData(String key, Object value) {
		data.put(key, value);
	}

	public void setSuccess() {
		setEc(Constants.RET_SUCCESS);
		setEm("Success!");
	}

	public void setError() {
		setEc(Constants.RET_COMMON_ERROR);
	}

	public void setError(String code) {
		setEc(code);
	}

	public void setError(String code, String message) {
		setEc(code);
		setEm(message);
	}
	public Map<String, Object> toMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("data",data);
		m.put("ec",ec);
		m.put("em",em);
		return m;
	}
 
}
