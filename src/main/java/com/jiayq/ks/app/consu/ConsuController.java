package com.jiayq.ks.app.consu;

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
import com.jiayq.ks.app.projectconsu.ProjectConsu;
import com.jiayq.ks.app.projectconsu.ProjectConsuService;

@RestController
@RequestMapping("/consu")
public class ConsuController extends BaseController {

	@Resource
	private ConsuService consuService;
	@Resource
	private ProjectConsuService projectConsuService;
	
	@RequestMapping("list")
	public Object all(Model model) {
		// TODO Auto-generated method stub
		String search = Variant.valueOf(model.asMap().get("search")).stringValue("");
		Page<Consu> page = consuService.findByName("%"+search+"%", getPage());
		return SUCCESS_GRID(page);
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	public Object doform(Consu consu) {
		consuService.save(consu);
		return SUCCESS();
	}
	
	@RequestMapping("/checkname")
	public Object checkname(String name) {
		List<Consu> list = consuService.findByName(name);
		if(list!=null && list.size()>0) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/belongProject")
	public Object mylist() {
		Page<Consu> page = consuService.findMyConsu(getCurrentUser().getProjectId(), getMaxPage());
		List<Consu> products = page.getContent();
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
		Page<Consu> page = consuService.findMyConsu(getCurrentUser().getProjectId(), getPage());
		for(int i=0; i<page.getContent().size(); i++) {
			Consu p = page.getContent().get(i);
			ProjectConsu pd = projectConsuService.findByProjectIdAndConsuId(getCurrentUser().getProjectId(), p.getId());
			p.setStatus(pd.getStatus());
		}
		return SUCCESS_GRID(page);
	}
	
	@RequestMapping(value="/addToProject",method = RequestMethod.POST)
	public Object add(String ids) {
		String projectId = getCurrentUser().getProjectId();
		if(ids!=null) {
			String[] idlist = ids.split(",");
			List<ProjectConsu> pflist = new ArrayList<>();
			if(idlist!=null && idlist.length>0) {
				Page<Consu> selectedConsulist = consuService.findMyConsu(projectId, getMaxPage());
				for(int i=0; i<idlist.length; i++) {
					if(!isExist(idlist[i], selectedConsulist.getContent())) {
						ProjectConsu pp = new ProjectConsu();
						pp.setProjectId(projectId);
						pp.setConsuId(idlist[i]);
						pp.setStatus(Constant.STATUS_ENABLE);
						pflist.add(pp);
					}
				}
				projectConsuService.saveAll(pflist);
			}
		}
		return SUCCESS();
	}
	
	@RequestMapping("start")
	public Object start(String id) {
		ProjectConsu pf = projectConsuService.findByProjectIdAndConsuId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_ENABLE);
		projectConsuService.save(pf);
		return SUCCESS();
	}
	
	@RequestMapping("stop")
	public Object stop(String id) {
		ProjectConsu pf = projectConsuService.findByProjectIdAndConsuId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_DISABLE);
		projectConsuService.save(pf);
		return SUCCESS();
	}
}
