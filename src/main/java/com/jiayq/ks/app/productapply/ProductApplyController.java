package com.jiayq.ks.app.productapply;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.menu.Menu;
import com.jiayq.ks.app.Constant;
import com.jiayq.ks.app.product.Product;
import com.jiayq.ks.app.product.ProductService;

@Controller
@RequestMapping("/productapply")
@Menu("productapply")
public class ProductApplyController extends BaseController {

	@Resource
	private ProductApplyService productApplyService;
	@Resource
	private ProductService productService;
	
	/**
	 * 产量申报
	 * @return
	 */
	@RequestMapping("form")
	@Menu("form")
	public String form(Model model) {
		ProductApply pa = new ProductApply();
		pa.setApplyerId(getCurrentUser().getId());
		pa.setApplyerName(getCurrentUser().getNickName());
		
		Page<Product> page = productService.findMyProduct(getCurrentUser().getProjectId(), getMaxPage());
		
		model.addAttribute("products", page.getContent());
		model.addAttribute("pa", pa);
		
		return "productapply/form";
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	@ResponseBody
	public Object form(ProductApply pa) {
		Product p = productService.findById(pa.getProductId()).get();
		pa.setProductName(p.getName());
		pa.setUnit(p.getUnit());
		pa.setProjectId(getCurrentUser().getProjectId());
		pa.setStatus(Constant.STATUS_ENABLE);
		productApplyService.save(pa);
		return SUCCESS();
	}
	
	@RequestMapping("/list")
	@Menu("list")
	public Object list() {
		return "productapply/list";
	}
	
	@RequestMapping("/listdata")
	@ResponseBody
	public Object listdata() {
		Page<ProductApply> page = productApplyService.findMyApply(getCurrentUser().getId(), getCurrentUser().getProjectId(), getPage());
		return  SUCCESS_GRID(page);
	}
	
	
}
