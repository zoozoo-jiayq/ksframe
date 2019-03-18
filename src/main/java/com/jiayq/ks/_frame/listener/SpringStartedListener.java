package com.jiayq.ks._frame.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jiayq.ks._frame.security.Role;
import com.jiayq.ks._frame.security.RoleService;

@Component
public class SpringStartedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private RoleService roleService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		roleService.initRoleInfo();
		
		Iterable<Role> roles = roleService.findAll();
		RoleCache.cacheRole(roles);
	}

}
