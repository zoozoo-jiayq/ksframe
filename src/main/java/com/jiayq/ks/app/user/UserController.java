package com.jiayq.ks.app.user;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.listener.SimpleCache;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.security.Role;
import com.jiayq.ks._frame.utils.MD5;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.Config;
import com.jiayq.ks.app.projectuserrole.ProjectUserRole;
import com.jiayq.ks.app.projectuserrole.ProjectUserRoleService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Resource
	private LoginUserService userService;
	@Resource
	private ProjectUserRoleService userRoleService;
	@Resource
	private Config config;
	
	@RequestMapping("list")
	public Object list() {
		Page<LoginUser> users =  userService.findByProjectId(getCurrentUser().getProjectId(),getPage());
		for(int i=0; i<users.getContent().size(); i++) {
			LoginUser lu = users.getContent().get(i);
			ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), lu.getId());
			if(pur!=null) {
				Role r = SimpleCache.getRoleById(pur.getRoleId());
				if(r!=null) {
					lu.setRole(r.getName());
				}else {
					lu.setRole("未知");
				}
			}else {
				lu.setRole("未知");
			}
		}
		return SUCCESS_PAGE(users);
	}
	
	@RequestMapping("comboxselect")
	public Object comboxselect() {
		Page<LoginUser> users =  userService.findByProjectId(getCurrentUser().getProjectId(),getMaxPage());
		for(int i=0; i<users.getContent().size(); i++) {
			LoginUser lu = users.getContent().get(i);
			ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), lu.getId());
			if(pur!=null) {
				Role r = SimpleCache.getRoleById(pur.getRoleId());
				if(r!=null) {
					lu.setRole(r.getName());
				}else {
					lu.setRole("未知");
				}
			}else {
				lu.setRole("未知");
			}
		}
		return users.getContent();
	}
	
	@RequestMapping("form")
	public Object form(String id) {
		LoginUser u = userService.findById(id).get();
		ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), u.getId());
		if(pur!=null) {
			u.setRole(pur.getRoleId());
		}
		return u;
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	public Object form(LoginUser loginUser) {
		
		if(StringUtils.isEmpty(loginUser.getId())){
			String pwd = config.getDefaultpwd();
			String encryPwd = MD5.encrypt(pwd);
			loginUser.setPassword(encryPwd);
			loginUser.setProjectName(loginUser.getProjectName());
			userService.save(loginUser);
			
			String roleId = loginUser.getRole();
			ProjectUserRole pur = new ProjectUserRole();
			pur.setProjectId(getCurrentUser().getProjectId());
			pur.setRoleId(roleId);
			pur.setUserId(loginUser.getId());
			userRoleService.save(pur);
		}else {
			LoginUser old = userService.findById(loginUser.getId()).get();
			super.merge(old, loginUser);
			ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(old.getProjectId(), old.getId());
			pur.setRoleId(loginUser.getRole());
			userService.save(old);
			userRoleService.save(pur);
		}
		
		return SUCCESS();
	}
	
	@RequestMapping("resetpwd")
	public Object resetpwd(String id) {
		LoginUser lu = userService.findById(id).get();
		lu.setPassword(MD5.encrypt("123456"));
		userService.save(lu);
		return SUCCESS();
	}
}
