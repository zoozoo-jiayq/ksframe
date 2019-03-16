package com.jiayq.ks.app.fee;

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
import com.jiayq.ks.app.projectfee.ProjectFee;
import com.jiayq.ks.app.projectfee.ProjectFeeService;

@RestController
@RequestMapping("/fee")
public class FeeController extends BaseController {

	@Resource
	private FeeService feeService;
	@Resource
	private ProjectFeeService projectFeeService;
	
	@RequestMapping("list")
	public Object all(Model model) {
		// TODO Auto-generated method stub
		String search = Variant.valueOf(model.asMap().get("search")).stringValue("");
		Page<Fee> page = feeService.findByName("%"+search+"%", getPage());
		return SUCCESS_GRID(page);
	}
	
	@RequestMapping(value="form",method = RequestMethod.POST)
	public Object doform(Fee fee) {
		feeService.save(fee);
		return SUCCESS();
	}
	
	
	/**
	 * 已选费用类型
	 * @param model
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/selectedlist")
	public Object mylist(Model model) {
		Page<Fee> page = feeService.findMyFee(getCurrentUser().getProjectId(), getPage());
		for(int i=0; i<page.getContent().size(); i++) {
			Fee f = page.getContent().get(i);
			ProjectFee pf = projectFeeService.findByProjectIdAndFeeId(getCurrentUser().getProjectId(), f.getId());
			f.setStatus(pf.getStatus());
		}
		return SUCCESS_GRID(page);
	}
	
	@RequestMapping(value="/addToProject",method = RequestMethod.POST)
	public Object add(String ids) {
		String projectId = getCurrentUser().getProjectId();
		if(ids!=null) {
			String[] idlist = ids.split(",");
			List<ProjectFee> pflist = new ArrayList<>();
			if(idlist!=null && idlist.length>0) {
				Page<Fee> selectedFeelist = feeService.findMyFee(projectId, getMaxPage());
				for(int i=0; i<idlist.length; i++) {
					if(!isExist(idlist[i], selectedFeelist.getContent())) {
						ProjectFee pp = new ProjectFee();
						pp.setProjectId(projectId);
						pp.setFeeId(idlist[i]);
						pp.setStatus(Constant.STATUS_ENABLE);
						pflist.add(pp);
					}
				}
				projectFeeService.saveAll(pflist);
			}
		}
		return SUCCESS();
	}
	
	@RequestMapping("start")
	public Object start(String id) {
		ProjectFee pf = projectFeeService.findByProjectIdAndFeeId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_ENABLE);
		projectFeeService.save(pf);
		return SUCCESS();
	}
	
	@RequestMapping("stop")
	public Object stop(String id) {
		ProjectFee pf = projectFeeService.findByProjectIdAndFeeId(getCurrentUser().getProjectId(), id);
		pf.setStatus(Constant.STATUS_DISABLE);
		projectFeeService.save(pf);
		return SUCCESS();
	}
}
