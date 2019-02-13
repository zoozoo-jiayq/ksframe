package com.jiayq.ks.app.project;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiayq.ks._frame.base.BaseController;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController{

	@Resource
	private ProjectService projectService;

	@RequestMapping("/list")
	public String list(Model model){
		Page<Project> page = projectService.findMyProject(getCurrentUser().getId(), getPage());
		model.addAttribute("page", page);
		return "project/list";
	}
	
	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("project", new Project());
		return "project/form";
	}
	
	public String doform(Project model) {
		// TODO Auto-generated method stub
		projectService.save(model);
		return "redirect:/project/list";
	}
	
	
}