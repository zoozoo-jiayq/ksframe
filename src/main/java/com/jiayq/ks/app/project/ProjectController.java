package com.jiayq.ks.app.project;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController{

	@Resource
	private ProjectService projectService;

	@RequestMapping("/list")
	public String list(ModelMap model){

		return "project/list";
	}

}