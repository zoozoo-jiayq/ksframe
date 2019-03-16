package com.jiayq.ks.app.feeapply;

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
import com.jiayq.ks.app.fee.Fee;
import com.jiayq.ks.app.fee.FeeService;
import com.jiayq.ks.app.flowconfig.FlowAPI;
import com.jiayq.ks.app.flowconfig.Workflow;
import com.jiayq.ks.app.flowconfig.WorkflowInstance;
import com.jiayq.ks.app.flowconfig.WorkflowInstanceService;
import com.jiayq.ks.app.flowconfig.WorkflowTask;

@Controller
@RequestMapping("/feeapply")
public class FeeApplyController extends BaseController {

	@Resource
	private FeeService feeService;
	@Resource
	private FeeApplyService feeApplyService;
	@Resource
	private FlowAPI flowAPI;
	@Resource
	private WorkflowInstanceService instanceService;
	
	@RequestMapping("form")
	public String form(Model model) {
		FeeApply fa = new FeeApply();
		fa.setApplyDate(new Date());
		fa.setApplyerId(getCurrentUser().getId());
		fa.setApplyerName(getCurrentUser().getNickName());
		fa.setProjectId(getCurrentUser().getProjectId());
		model.addAttribute("fa", fa);
		
		List<Fee> feelist = feeService.findMyFee(getCurrentUser().getProjectId(), getMaxPage()).getContent();
		model.addAttribute("fees", feelist);
		return "feeapply/form";
	}
	
	@ResponseBody
	@RequestMapping( value = "form",method = RequestMethod.POST)
	public Object doform(FeeApply fa) {
		
		String feeTypeId = fa.getFeeTypeId();
		Fee f = feeService.findById(feeTypeId).get();
		fa.setFeeTypeName(f.getName());
		WorkflowTask task = flowAPI.start(getCurrentUser().getProjectId(), Workflow.FLOW_TYPE_FEE);
		fa.setWorkflowInstanceId(task.getWorkflowInstanceId());
		feeApplyService.save(fa);
		
		
		return SUCCESS();
	}
	
	@RequestMapping(value="list")
	public Object list() {
		return "feeapply/list";
	}
	
	@RequestMapping("myapply")
	@ResponseBody
	public Object myapply() {
		Page<FeeApply> page = feeApplyService.findByApply(getCurrentUser().getId(),getCurrentUser().getProjectId(), getMaxPage());
		page.getContent().stream().forEach(fa->{
			WorkflowInstance instance = instanceService.findById(fa.getWorkflowInstanceId()).get();
			fa.setStatus(instance.getStatus());
		});
		return SUCCESS_GRID(page);
	}
}
