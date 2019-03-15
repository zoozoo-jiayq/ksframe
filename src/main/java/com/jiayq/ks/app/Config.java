package com.jiayq.ks.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("${app.defaultpwd}")
	private String defaultpwd;

	public String getDefaultpwd() {
		return defaultpwd;
	}

	public void setDefaultpwd(String defaultpwd) {
		this.defaultpwd = defaultpwd;
	}
	
	
}
