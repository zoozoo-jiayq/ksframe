package com.jiayq.ks.app.flowconfig;

import java.sql.Date;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jiayq.ks._frame.base.BaseController;

@RequestMapping("/flowconfig")
@Controller
public class FlowConfigController extends BaseController {

	@Resource
	private WorkflowVersionService versionService;
	
	@RequestMapping("index")
	public Object feeconfig() {
		return "flowconfig/index";
	}
	
	@RequestMapping(value="save",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Object save(String workflowtype,@RequestBody String config) {
		
		int v = 0;
		WorkFlowVersion wfv = versionService.findActiveFlow(getCurrentUser().getProjectId(),workflowtype);
		if(wfv!=null) {
			v = wfv.getVersion();
		}
		
		WorkFlowVersion version = new  WorkFlowVersion();
		version.setConfig(config);
		version.setInserttime(new Date(Calendar.getInstance().getTimeInMillis()));
		version.setInsertUserId(getCurrentUser().getId());
		version.setProjectId(getCurrentUser().getProjectId());
		version.setWorkflowtype(workflowtype);
		version.setVersion(v+1);
		versionService.save(version);
		return SUCCESS();
	}
	
	@RequestMapping("load")
	@ResponseBody
	public Object load(String workflowtype) {
		WorkFlowVersion wfv = versionService.findActiveFlow(getCurrentUser().getProjectId(), workflowtype);
		if(wfv!=null) {
			return SUCCESS(wfv.getConfig());
		}else {
			return SUCCESS();
		}
	}
	
}
