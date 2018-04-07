package com.zjk.result;

public class Result {

	public int status = 1; // 1-成功，0-失败
	public String errMsg = "";

	public boolean isOk() {
		return status == 1;
	}
}
