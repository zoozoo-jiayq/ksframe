package com.jiayq.ks.app.productapply;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks.app.product.Product;
import com.jiayq.ks.app.product.ProductService;

@Controller
@RequestMapping("/productapply")
public class ProductApplyController extends BaseController {

	@Resource
	private ProductApplyService productApplyService;
	@Resource
	private ProductService productService;
	
	@RequestMapping("/{projectId}/myapply")
	public String myapply(Model model,@PathVariable("projectId")String projectId) {
		Page<ProductApply> page = productApplyService.findMyApply(super.getCurrentUser().getId(),projectId, getPage());
		model.addAttribute("page", page);
		return "productapply/myapply";
	}
	
	@RequestMapping(value = "/{projectId}/add",method = RequestMethod.POST)
	public String apply(@PathVariable("projectId")String projectId,String productId,double amount) {
		Product product = productService.findById(productId).get();
		ProductApply pa = new ProductApply();
		pa.setProjectId(projectId);
		pa.setProductId(productId);
		pa.setApplyDate(new Date());
		pa.setApplyerId(getCurrentUser().getId());
		pa.setAmount(amount);
		pa.setProductName(product.getName());
		pa.setUnit(product.getUnit());
		productApplyService.save(pa);
		return "redirect:";
	}
}
