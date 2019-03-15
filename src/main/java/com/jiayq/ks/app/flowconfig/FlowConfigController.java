package com.jiayq.ks.app.flowconfig;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.Variant;

@RequestMapping("/flowconfig")
@Controller
public class FlowConfigController extends BaseController {

	@Resource
	private WorkflowService workflowService;
	@Resource
	private WorkflowVersionService versionService;
	
	@RequestMapping("index")
	public Object feeconfig() {
		return "flowconfig/list";
	}
	
	@ResponseBody
	@RequestMapping(value="form",method = RequestMethod.POST)
	public Object form(Workflow workflow) {
		Workflow wfs = workflowService.findByType(getCurrentUser().getProjectId(), workflow.getType());
		if(wfs == null  ) {
			workflowService.save(workflow);
			return SUCCESS();
		}else {
			return ERROR("repeat");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="list")
	public Object list() {
		return SUCCESS_PAGE(workflowService.findByProjectId(getCurrentUser().getProjectId(), getPage()));
	}
	
	@ResponseBody
	@RequestMapping(value="versionlist")
	public Object versionlist(String workflowId) {
		Page<WorkFlowVersion> vertions = versionService.findByWorkflowId(workflowId,getPage());
		return SUCCESS_PAGE(vertions);
	}
	
	@RequestMapping("design")
	public Object design(Model model,String workflowId,String versionId) {
		model.addAttribute("workflowId", workflowId);
		model.addAttribute("versionId", versionId);
		return "flowconfig/design";
	}
	
	/**
	 * 初始化版本
	 * @param worklfowId
	 * @param versionId
	 * @return
	 */
	/**
	 * @param worklfowId
	 * @param versionId
	 * @return
	 */
	@RequestMapping("versionInit")
	@ResponseBody
	public Object versionInit(String workflowId,String versionId ) {
		if(StringUtils.isEmpty(versionId)) {
			List<WorkFlowVersion> versions = versionService.findByWorkflowId(workflowId, getMaxPage()).getContent();
			if(versions == null || versions.size() == 0) {
				WorkFlowVersion v = new WorkFlowVersion();
				v.setWorkflowId(workflowId);
				v.setVersion("V1");
				v.setStatus(WorkFlowVersion.VERSION_STATUS_DISABLE);
				return SUCCESS(v);
			}else {
				WorkFlowVersion curmax = versions.get(0);
				String maxversion = curmax.getVersion().substring(1);
				int v = Variant.valueOf(maxversion).intValue(0);
				WorkFlowVersion wfv = new WorkFlowVersion();
				wfv.setVersion("V"+(v+1));
				wfv.setWorkflowId(workflowId);
				wfv.setStatus(WorkFlowVersion.VERSION_STATUS_DISABLE);
				return SUCCESS(wfv);
			}
		}else {
			WorkFlowVersion version = versionService.findById(versionId).get();
			return SUCCESS(version);
		}
	}
	
	@RequestMapping(value="saveVersion",method = RequestMethod.POST)
	@ResponseBody
	public Object saveVersion(WorkFlowVersion version) {
		WorkFlowVersion v = versionService.save(version);
		this.updateStatus(v.getId(), v.getStatus());
		return SUCCESS(v);
	}
	
	@RequestMapping("updateStatus")
	@ResponseBody
	public Object updateStatus(String versionId,int status) {
		WorkFlowVersion v = versionService.findById(versionId).get();
		List<WorkFlowVersion> versions = versionService.findByWorkflowId(v.getWorkflowId(), getMaxPage()).getContent();
		for(int i=0; i<versions.size(); i++) {
			WorkFlowVersion t = versions.get(i);
			t.setStatus(WorkFlowVersion.VERSION_STATUS_DISABLE);
			if(t.getId().equals(versionId)) {
				t.setStatus(status);
			}
		}
		versionService.saveAll(versions);
		return SUCCESS();
	}
}
