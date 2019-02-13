package com.jiayq.ks.app.product;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.Variant;
import com.jiayq.ks.app.projectproduct.ProjectProduct;
import com.jiayq.ks.app.projectproduct.ProjectProductService;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Resource
	private ProductService productService;
	@Resource
	private ProjectProductService projectProductService;
	
	@RequestMapping("list")
	@Override
	public String list(Model model) {
		// TODO Auto-generated method stub
		String search = Variant.valueOf(model.asMap().get("search")).stringValue("");
		Page<Product> page = productService.findByName("%"+search+"%", getPage());
		model.addAttribute("page", page);
		return "product/list";
	}
	
	@RequestMapping("/form")
	@Override
	public String form(Model model) {
		// TODO Auto-generated method stub
		model.addAttribute("product", new Product());
		return "product/form";
	}
	
	@RequestMapping(value = "/form",method = RequestMethod.POST)
	public String doform(Product product) {
		productService.save(product);
		return "redirect:/product/list";
	}
	
	@RequestMapping("/{projectId}/list")
	public String mylist(Model model,@PathVariable("projectId")String projectId) {
		Page<Product> page = productService.findMyProduct(projectId, getPage());
		model.addAttribute("page", page);
		return "product/mylist";
	}
	
	@RequestMapping(value="/{projectId}/form",method = RequestMethod.POST)
	public String add(Model model,@PathVariable("projectId")String projectId,String productId) {
		ProjectProduct pp = new ProjectProduct();
		pp.setProjectId(projectId);
		pp.setProductId(productId);
		projectProductService.save(pp);
		return "redirect:/product/"+projectId+"/list";
	}
}
