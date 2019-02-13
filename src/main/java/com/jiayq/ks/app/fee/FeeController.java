package com.jiayq.ks.app.fee;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.Variant;
import com.jiayq.ks.app.product.Product;
import com.jiayq.ks.app.projectfee.ProjectFee;
import com.jiayq.ks.app.projectfee.ProjectFeeService;
import com.jiayq.ks.app.projectproduct.ProjectProduct;

@Controller
@RequestMapping("/fee")
public class FeeController extends BaseController {

	@Resource
	private FeeService feeService;
	@Resource
	private ProjectFeeService projectFeeService;
	
	@RequestMapping("list")
	@Override
	public String list(Model model) {
		// TODO Auto-generated method stub
		String search = Variant.valueOf(model.asMap().get("search")).stringValue("");
		Page<Fee> page = feeService.findByName("%search%", getPage());
		model.addAttribute("page", page);
		return "fee/list";
	}
	
	@Override
	@RequestMapping("form")
	public String form(Model model) {
		// TODO Auto-generated method stub
		Fee fee = new Fee();
		model.addAttribute("fee", fee);
		return "fee/form";
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	public String doform(Fee fee) {
		feeService.save(fee);
		return "redirect:/fee/list";
	}
	
	@RequestMapping("/{projectId}/list")
	public String mylist(Model model,@PathVariable("projectId")String projectId) {
		Page<Fee> page = feeService.findMyFee(projectId, getPage());
		model.addAttribute("page", page);
		return "fee/mylist";
	}
	
	@RequestMapping(value="/{projectId}/form",method = RequestMethod.POST)
	public String add(Model model,@PathVariable("projectId")String projectId,String feeId) {
		ProjectFee pp = new ProjectFee();
		pp.setProjectId(projectId);
		pp.setFeeId(feeId);;
		projectFeeService.save(pp);
		return "redirect:/fee/"+projectId+"/list";
	}
}
