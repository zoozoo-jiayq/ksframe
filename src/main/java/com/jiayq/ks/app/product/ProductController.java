package com.jiayq.ks.app.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.Variant;
import com.jiayq.ks.app.Constant;
import com.jiayq.ks.app.projectproduct.ProjectProduct;
import com.jiayq.ks.app.projectproduct.ProjectProductService;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Resource
	private ProductService productService;
	@Resource
	private ProjectProductService projectProductService;
	
	@RequestMapping("list")
	public Object all(Model model) {
		// TODO Auto-generated method stub
		String search = Variant.valueOf(model.asMap().get("search")).stringValue("");
		Page<Product> page = productService.findByName("%"+search+"%", getPage());
		return SUCCESS_PAGE(page);
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	public Object doform(Product product) {
		productService.save(product);
		return SUCCESS();
	}
	
	@RequestMapping("/belongProject")
	public Object mylist() {
		Page<Product> page = productService.findMyProduct(getCurrentUser().getProjectId(), getMaxPage());
		List<Product> products = page.getContent();
		return products;
	}
	
	/**
	 * 已选产品类型
	 * @param model
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/selectedlist")
	public Object mylist(Model model) {
		Page<Product> page = productService.findMyProduct(getCurrentUser().getProjectId(), getPage());
		for(int i=0; i<page.getContent().size(); i++) {
			Product p = page.getContent().get(i);
			ProjectProduct pd = projectProductService.findByProjectIdAndProductId(getCurrentUser().getProjectId(), p.getId());
			p.setStatus(pd.getStatus());
		}
		return SUCCESS_PAGE(page);
	}
	
	@RequestMapping(value="/addToProject",method = RequestMethod.POST)
	public Object add(String ids) {
		String projectId = getCurrentUser().getProjectId();
		if(ids!=null) {
			String[] idlist = ids.split(",");
			List<ProjectProduct> pflist = new ArrayList<>();
			if(idlist!=null && idlist.length>0) {
				Page<Product> selectedProductlist = productService.findMyProduct(projectId, getMaxPage());
				for(int i=0; i<idlist.length; i++) {
					if(!isExist(idlist[i], selectedProductlist.getContent())) {
						ProjectProduct pp = new ProjectProduct();
						pp.setProjectId(projectId);
						pp.setProductId(idlist[i]);
						pp.setStatus(Constant.STATUS_ENABLE);
						pflist.add(pp);
					}
				}
				projectProductService.saveAll(pflist);
			}
		}
		return SUCCESS();
	}
	
	@RequestMapping("start")
	public Object start(String id) {
		ProjectProduct pf = projectProductService.findByProjectIdAndProductId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_ENABLE);
		projectProductService.save(pf);
		return SUCCESS();
	}
	
	@RequestMapping("stop")
	public Object stop(String id) {
		ProjectProduct pf = projectProductService.findByProjectIdAndProductId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_DISABLE);
		projectProductService.save(pf);
		return SUCCESS();
	}
}
