package com.jiayq.ks.app.consuapply;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks.app.consu.Consu;
import com.jiayq.ks.app.consu.ConsuService;
import com.jiayq.ks.app.fee.Fee;
import com.jiayq.ks.app.feeapply.FeeApply;
import com.jiayq.ks.app.flowconfig.FlowAPI;
import com.jiayq.ks.app.flowconfig.WorkFlowVersion;
import com.jiayq.ks.app.flowconfig.WorkflowInstance;
import com.jiayq.ks.app.flowconfig.WorkflowInstanceService;
import com.jiayq.ks.app.flowconfig.WorkflowTask;

@Controller
@RequestMapping("/consuapply")
public class ConsuApplyController extends BaseController {

	@Resource
	private ConsuService consuService;
	@Resource
	private ConsuApplyService consuApplyService;
	@Resource
	private FlowAPI flowAPI;
	@Resource
	private WorkflowInstanceService instanceService;
	
	@RequestMapping("form")
	public String form(Model model) {
		ConsuApply fa = new ConsuApply();
		fa.setApplyDate(new Date());
		fa.setApplyerId(getCurrentUser().getId());
		fa.setApplyerName(getCurrentUser().getNickName());
		fa.setProjectId(getCurrentUser().getProjectId());
		model.addAttribute("fa", fa);
		
		List<Consu> consulist = consuService.findMyConsu(getCurrentUser().getProjectId(), getMaxPage()).getContent();
		model.addAttribute("consus", consulist);
		return "consuapply/form";
	}
	
	@ResponseBody
	@RequestMapping( value = "form",method = RequestMethod.POST)
	public Object doform(ConsuApply ca) {
		
		String consuTypeId = ca.getConsuTypeId();
		Consu f = consuService.findById(consuTypeId).get();
		ca.setConsuTypeName(f.getName());
		WorkflowTask task = flowAPI.start(getCurrentUser().getProjectId(), WorkFlowVersion.TYPE_CONSU);
		ca.setWorkflowInstanceId(task.getWorkflowInstanceId());
		ca.setUnit(f.getUnit());
		consuApplyService.save(ca);
		return SUCCESS();
	}
	
	@RequestMapping(value="list")
	public Object list() {
		return "consuapply/list";
	}
	
	@RequestMapping("myapply")
	@ResponseBody
	public Object myapply() {
		Page<ConsuApply> page = consuApplyService.findByApply(getCurrentUser().getId(),getCurrentUser().getProjectId(),getPage());
		page.getContent().stream().forEach(fa->{
			WorkflowInstance instance = instanceService.findById(fa.getWorkflowInstanceId()).get();
			fa.setStatus(instance.getStatus());
		});
		return SUCCESS_GRID(page);
	}
}
