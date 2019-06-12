package com.jiayq.ks.app.role;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.listener.RoleCache;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{

	@RequestMapping("list")
	public Object list() {
		return RoleCache.getRole();
	}
}
