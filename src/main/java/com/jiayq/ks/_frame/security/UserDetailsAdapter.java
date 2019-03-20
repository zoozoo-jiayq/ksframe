package com.jiayq.ks._frame.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.jiayq.ks._frame.utils.SpringContextHolder;
import com.jiayq.ks.app.projectuserrole.ProjectUserRole;
import com.jiayq.ks.app.projectuserrole.ProjectUserRoleService;

public class UserDetailsAdapter implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoginUser user;
	
	public LoginUser getUser() {
		return user;
	}

	public void setUser(LoginUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> r = new ArrayList<>();
		LoginUserService userService = SpringContextHolder.getBean(LoginUserService.class);
		ProjectUserRoleService purService = SpringContextHolder.getBean(ProjectUserRoleService.class);
		RoleService roleService = SpringContextHolder.getBean(RoleService.class);
		
		LoginUser u = userService.findById(user.getId()).get();
		
		if(!StringUtils.isEmpty(u.getProjectId())) {
			ProjectUserRole pur = purService.findOneByProjectIdAndUserId(u.getProjectId(), u.getId());
			if(pur!=null) {
				Role role = roleService.findById(pur.getRoleId()).get();
				r.add(new SimpleGrantedAuthority(role.getCode()));
			}
		}
		
		return r;
	}
	
	public boolean hasAuth(String roleCode) {
		Collection<? extends GrantedAuthority> auths = this.getAuthorities();
		if(auths.size() == 0 ) {
			return true;
		}
		if(StringUtils.isEmpty(roleCode)) {
			return true;
		}
		if(roleCode.equals("all")) {
			return true;
		}
		for(GrantedAuthority g:auths) {
			String[] codes = roleCode.split(",");
			for(String c:codes) {
				if(g.getAuthority().equals(c)) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		if(user == null) {
			return  "";
		}
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		if(user == null) {
			return true;
		}
		return user.isAccountNonExpired();
		
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		if(user == null) {
			return true;
		}
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		if(user == null) {
			return true;
		}
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		if(user == null) {
			return true;
		}
		return user.isEnabled();
	}

	

}
