package com.jiayq.ks.app.project;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.menu.Menu;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.security.RoleService;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.fee.FeeService;
import com.jiayq.ks.app.product.ProductService;

@Controller
@RequestMapping("/project")
@Menu("project")
public class ProjectController extends BaseController{

	@Resource
	private ProjectService projectService;
	@Resource
	private RoleService roleService;
	@Resource
	private FeeService feeService; 
	@Resource
	private ProductService productService;
	@Resource
	private LoginUserService userService;
	
	@Menu("form")
	@RequestMapping("/form")
	public String form(Model model,String projectId) {
		Project p = new Project();
		if(StringUtils.isNotEmpty(projectId)) {
			p = projectService.findById(projectId).get();
		}
		model.addAttribute("project", p);
		return "project/form";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	@ResponseBody
	public Object doform(Project model) {
		// TODO Auto-generated method stub
		LoginUser u = getCurrentUser();
		Project dest = null;
		if(StringUtils.isNotEmpty(model.getId())) {
			dest = projectService.findById(model.getId()).get();
			merge(dest, model);
		}else {
			dest = model;
		}
		
		Project np = projectService.createProject(u, dest);
		u.setProjectId(np.getId());
		u.setProjectName(np.getName());
		super.updateCurrentUser(u);
		return SUCCESS(np);
	}
	
	@RequestMapping("/select/{id}")
	public String select(@PathVariable("id")String id) {
		Project p = projectService.findById(id).get();
		LoginUser u = getCurrentUser();
		u.setProjectId(id);
		u.setProjectName(p.getName());
		updateCurrentUser(u);
		return "redirect:/";
	}
	
	@Menu("config")
	@RequestMapping("config")
	public String fee(Model model) {
		return "project/config";
	}
	
	@RequestMapping("/configinfo")
	@ResponseBody
	public Object configInfo() {
		String projectId = getCurrentUser().getProjectId();
		
		int feesize = feeService.findMyFee(projectId, getMaxPage()).getContent().size();
		int productsize = productService.findMyProduct(projectId, getMaxPage()).getContent().size();
		int usersize = userService.findByProjectId(projectId, getMaxPage()).getContent().size();
		
		Map<String,String> result = new HashMap<>();
		result.put("fee", feesize+"");
		result.put("product", productsize+"");
		result.put("user", usersize+"");
		
		return SUCCESS(result);
	}
	
}