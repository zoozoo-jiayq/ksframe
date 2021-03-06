package com.jiayq.ks._frame.freemarker;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class FreemarkerConfig {

	@Resource
	private freemarker.template.Configuration configuration;
	@Resource
	private AuthCtlDirective auth;
	
	@PostConstruct
	public void init() {
		configuration.setSharedVariable("auth", auth);
	}
}
