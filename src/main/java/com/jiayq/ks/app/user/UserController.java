package com.jiayq.ks.app.user;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.listener.RoleCache;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.security.Role;
import com.jiayq.ks._frame.utils.MD5;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.Config;
import com.jiayq.ks.app.projectuser.ProjectUser;
import com.jiayq.ks.app.projectuser.ProjectUserService;
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
	@Resource
	private ProjectUserService projectUserService;
	
	@RequestMapping("list")
	public Object list() {
		Page<LoginUser> users =  userService.findByProjectId(getCurrentUser().getProjectId(),getPage());
		for(int i=0; i<users.getContent().size(); i++) {
			LoginUser lu = users.getContent().get(i);
			ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), lu.getId());
			if(pur!=null) {
				Role r = RoleCache.getRoleById(pur.getRoleId());
				if(r!=null) {
					lu.setRole(r.getName());
				}else {
					lu.setRole("未知");
				}
			}else {
				lu.setRole("未知");
			}
		}
		return SUCCESS_GRID(users);
	}
	
	@RequestMapping("comboxselect")
	public Object comboxselect() {
		Page<LoginUser> users =  userService.findByProjectId(getCurrentUser().getProjectId(),getMaxPage());
		for(int i=0; i<users.getContent().size(); i++) {
			LoginUser lu = users.getContent().get(i);
			ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), lu.getId());
			if(pur!=null) {
				Role r = RoleCache.getRoleById(pur.getRoleId());
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
		
		String pwd = config.getDefaultpwd();
		String encryPwd = MD5.encrypt(pwd);
		loginUser.setPassword(encryPwd);
		loginUser.setProjectName(getCurrentUser().getProjectName());
		LoginUser nlu = userService.save(loginUser);
		
		ProjectUser pu = new ProjectUser();
		pu.setProjectId(getCurrentUser().getProjectId());
		pu.setUserId(nlu.getId());
		projectUserService.save(pu);
		
		String roleId = loginUser.getRole();
		
		ProjectUserRole pur = new ProjectUserRole();
		pur.setProjectId(getCurrentUser().getProjectId());
		pur.setRoleId(roleId);
		pur.setUserId(nlu.getId());
		userRoleService.save(pur);
		
		return SUCCESS();
	}
	
	@RequestMapping("resetpwd")
	public Object resetpwd(String id) {
		LoginUser lu = userService.findById(id).get();
		lu.setPassword(MD5.encrypt("123456"));
		userService.save(lu);
		return SUCCESS();
	}
	
	@RequestMapping("search")
	public Object search(String phone) {
		List<LoginUser> users = userService.findByPhone(phone);
		if(users.size() > 0 ) {
			for(int i=0; i<users.size(); i++) {
				ProjectUser pu = projectUserService.findByProjectIdAndUserId(getCurrentUser().getProjectId(), users.get(i).getId());
				if(pu!=null) {
					users.get(i).setExistInProject(true);
				}else {
					users.get(i).setExistInProject(false);
				}
			}
		}
		return SUCCESS(users);
	}
	
	@RequestMapping("addToProject")
	public Object addToProject(String userId) {
		ProjectUser pu = new ProjectUser();
		pu.setProjectId(getCurrentUser().getProjectId());
		pu.setUserId(userId);
		ProjectUser npu = projectUserService.save(pu);
		
		ProjectUserRole pur = new ProjectUserRole();
		pur.setProjectId(getCurrentUser().getProjectId());
		pur.setUserId(userId);
		Role r = RoleCache.getRoleByCode(Role.BUSINESSER);
		pur.setRoleId(r.getId());
		userRoleService.save(pur);
		
		Optional<LoginUser> lu = userService.findById(userId);
		if(lu.isPresent()) {
			LoginUser luer = lu.get();
			if(StringUtils.isEmpty(luer.getProjectId())) {
				luer.setProjectId(getCurrentUser().getProjectId());
				luer.setProjectName(getCurrentUser().getProjectName());
				userService.save(luer);
			}
		}
		
		return SUCCESS();
	}
	
	@RequestMapping("updaterole")
	public Object updaterole(String userId,String roleId) {
		ProjectUserRole pur = userRoleService.findOneByProjectIdAndUserId(getCurrentUser().getProjectId(), userId);
		pur.setRoleId(roleId);
		userRoleService.save(pur);
		return SUCCESS();
	}
}
