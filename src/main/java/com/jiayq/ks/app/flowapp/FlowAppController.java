package com.jiayq.ks.app.flowapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.feeapply.FeeApply;
import com.jiayq.ks.app.feeapply.FeeApplyService;
import com.jiayq.ks.app.flowconfig.FlowAPI;
import com.jiayq.ks.app.flowconfig.WorkflowInstance;
import com.jiayq.ks.app.flowconfig.WorkflowInstanceService;
import com.jiayq.ks.app.flowconfig.WorkflowTask;
import com.jiayq.ks.app.flowconfig.WorkflowTaskService;

@Controller
@RequestMapping("/flowapp")
public class FlowAppController extends BaseController {

	@Resource
	private WorkflowInstanceService instanceService;
	@Resource
	private FeeApplyService applyService;
	@Resource
	private WorkflowTaskService taskService;
	@Resource
	private FlowAPI flowAPI;
	@Resource
	private LoginUserService userService;
	
	@RequestMapping("todetail")
	public Object todetail(Model model,String instanceId,String taskId) {
		WorkflowInstance instance = instanceService.findById(instanceId).get();
		FeeApply apply = applyService.findByInstanceId(instanceId);
		List<WorkflowTask> tasks = taskService.findByInstanceId(instanceId, new Sort(Direction.ASC,DEFAULT_SORT_PROPERTY));
		model.addAttribute("apply", apply);
		model.addAttribute("instance", instance);
		model.addAttribute("tasks", tasks);
		model.addAttribute("taskId", taskId);
		return "flowapp/detail";
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		
		return "flowapp/list";
	}
	
	@ResponseBody
	@RequestMapping("waitapprove")
	public Object waitapprove() {
		Page<WorkflowTask> tasks =  flowAPI.findWaitApprove(getCurrentUser().getProjectId(), getCurrentUser().getId(), getPage());
		List<Map<String,String>> list = tasks.getContent().stream().map(task->{
			return tasktomap(task);
		}).collect(Collectors.toList());
		return SUCCESS_GRID(tasks.getTotalElements(),list);
	}
	
	@ResponseBody
	@RequestMapping("approvehistory")
	public Object approvehistory() {
		Page<WorkflowTask> tasks = flowAPI.findApproveHistory(getCurrentUser().getProjectId(), getCurrentUser().getId(), getPage(new Sort(Direction.DESC,"processDate")));
		List<Map<String,String>> list = tasks.getContent().stream().map(task->{
			return tasktomap(task);
		}).collect(Collectors.toList());
		return SUCCESS_GRID(tasks.getTotalElements(), list);
	}
	
	@ResponseBody
	@RequestMapping( value = "approve",method = RequestMethod.POST)
	public Object approve(Approve approve) {
		if(approve.getApprove() == WorkflowTask.TASK_STATUS_END) {
			flowAPI.passTask(approve.getTaskId(), approve.getRemark());
		}else {
			flowAPI.reject(approve.getTaskId(), approve.getRemark());
		}
		return SUCCESS();
	}
	
	/**
	 * @param task
	 * @return
	 */
	private Map<String,String> tasktomap(WorkflowTask task){
		WorkflowInstance instance = instanceService.findById(task.getWorkflowInstanceId()).get();
		LoginUser lu = userService.findById(instance.getInsertUserId()).get();
		Map<String,String> r = new HashMap<>();
		r.put("workflowName",instance.getWorkflowName());
		r.put("applyerName", lu.getNickName());
		r.put("applyDate", StringUtils.formatDate(instance.getInserttime(), "yyyy-MM-dd"));
		long h = (System.currentTimeMillis()-task.getInserttime().getTime())/(3600*1000);
		r.put("wait", h+"");
		r.put("taskId", task.getId());
		r.put("instanceId",task.getWorkflowInstanceId());
		if(task.getProcessDate()!=null) {
			r.put("processDate", StringUtils.formatDate(task.getProcessDate(),"yyyy-MM-dd"));
			
		}
		return r;
	}
	
}
