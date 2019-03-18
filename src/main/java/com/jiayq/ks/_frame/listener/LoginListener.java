package com.jiayq.ks._frame.listener;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jiayq.ks._frame.security.LoginController;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginedEvent;
import com.jiayq.ks.app.project.Project;
import com.jiayq.ks.app.project.ProjectService;

@Component
public class LoginListener implements ApplicationListener<LoginedEvent> {
	public static String MY_PROJECTS = "my_projects";
	
	@Resource
	private ProjectService projectService;

	@Override
	public void onApplicationEvent(LoginedEvent event) {
		// TODO Auto-generated method stub
		HttpSession s = (HttpSession) event.getSource();
		// TODO Auto-generated method stub
		LoginUser user = (LoginUser) s.getAttribute(LoginController.LOGIN_USER_KEY);
		List<Project> projects = projectService.findMyProject(user.getId());
		if(projects!=null && projects.size()>0) {
			projects.sort(new Comparator<Project>() {
				@Override
				public int compare(Project o1, Project o2) {
					// TODO Auto-generated method stub
					if(o1.getId().equals(user.getProjectId())) {
						return -1;
					}
					return 0;
				}
			});
			s.setAttribute(MY_PROJECTS, projects);
		}
		
		s.setAttribute("roles", RoleCache.getRole());
	}
	


}
