package com.example.Response;

public class AuthResponse {
	
	private String jwt;
	private String msg;
	
	public AuthResponse(String jwt, String msg) {
		super();
		this.jwt = jwt;
		this.msg = msg;
	}

	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
