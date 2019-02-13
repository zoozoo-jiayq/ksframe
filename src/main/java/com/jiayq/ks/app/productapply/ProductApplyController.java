package com.jiayq.ks.app.productapply;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiayq.ks._frame.base.BaseController;

@Controller
@RequestMapping("/productapply")
public class ProductApplyController extends BaseController {

	@Resource
	private ProductApplyService productApplyService;
	
	@RequestMapping("/{projectId}/myapply")
	public String myapply(Model model,@PathVariable("projectId")String projectId) {
		Page<ProductApply> page = productApplyService.findMyApply(super.getCurrentUser().getId(),projectId, getPage());
		model.addAttribute("page", page);
		return "productapply/myapply";
	}
	
	@RequestMapping(value = "add",method = RequestMethod.POST)
	public String apply() {
		
		return null;
	}
}
