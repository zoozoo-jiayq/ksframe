package com.jiayq.ks.app.project;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseServiceProxy;
import com.jiayq.ks._frame.listener.SimpleCache;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.security.Role;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.projectuser.ProjectUser;
import com.jiayq.ks.app.projectuser.ProjectUserService;
import com.jiayq.ks.app.projectuserrole.ProjectUserRole;
import com.jiayq.ks.app.projectuserrole.ProjectUserRoleService;

@Service
@Transactional
public class ProjectService extends BaseServiceProxy<Project>{

	@Resource
	private ProjectDao projectDao;
	@Resource
	private ProjectUserService projectUserService;
	@Resource
	private ProjectUserRoleService projectUserRoleService;
	@Resource
	private LoginUserService loginUserService;
	
	@Override
	protected com.jiayq.ks._frame.base.BaseRepository<Project> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectDao;
	}
	
	public Page<Project> findMyProject(String userId,Pageable page){
		return projectDao.findMyProject(userId,page);
	}
	
	public List<Project> findMyProject(String userId){
		return projectDao.findMyProject(userId);
	}
	
	public void createProject(LoginUser u,Project p) {
		if(StringUtils.isNotEmpty(p.getId())) {
			projectDao.save(p);
		}else {
			p = projectDao.save(p);

			ProjectUser pu = new ProjectUser();
			pu.setProjectId(p.getId());
			pu.setUserId(u.getId());
			projectUserService.save(pu);
			
			ProjectUserRole pur = new ProjectUserRole();
			pur.setProjectId(p.getId());
			pur.setUserId(u.getId());
			Role r = SimpleCache.getRoleByCode(Role.ADMIN);
			pur.setRoleId(r.getId());
			projectUserRoleService.save(pur);
		}
		
	}
	
}