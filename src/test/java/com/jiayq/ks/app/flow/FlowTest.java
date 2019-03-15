package com.jiayq.ks.app.flow;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiayq.ks.KsApplication;
import com.jiayq.ks.app.flowconfig.FlowAPI;
import com.jiayq.ks.app.flowconfig.WorkFlowVersion;
import com.jiayq.ks.app.flowconfig.Workflow;
import com.jiayq.ks.app.flowconfig.WorkflowInstance;
import com.jiayq.ks.app.flowconfig.WorkflowInstanceService;
import com.jiayq.ks.app.flowconfig.WorkflowService;
import com.jiayq.ks.app.flowconfig.WorkflowTask;
import com.jiayq.ks.app.flowconfig.WorkflowVersionService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KsApplication.class})
public class FlowTest {

	private String projectId = "4028be8168f6d2780168f6d2aebb0000";
	private String userId1 = "4028be81690a750f01690a781e9d0002";
	private String userId2 = "324";
	
	@Resource
	private FlowAPI flowAPI;
	@Resource
	private WorkflowInstanceService instanceService;
//	
//	/**
//	 * 
//	 */
//	@Test
//	public void testForward() {
//		
//		WorkflowTask task = flowAPI.start(projectId, Workflow.FLOW_TYPE_FEE);
//		Assert.assertNotNull(task);
//		
//		List<WorkflowTask> tasks = flowAPI.findWaitApprove(projectId, userId1);
//		Assert.assertTrue(tasks.size() == 1);
//		
//		flowAPI.passTask(tasks.get(0).getId());
//		List<WorkflowTask> waits = flowAPI.findWaitApprove(projectId, userId2);
//		Assert.assertTrue(waits.size() == 1);
//		List<WorkflowTask> his = flowAPI.findApproveHistory(projectId, userId1);
//		Assert.assertTrue(his.size() == 1);
//		
//		flowAPI.passTask(waits.get(0).getId());
//		his = flowAPI.findApproveHistory(projectId, userId2);
//		Assert.assertTrue(his.size() == 1);
//		
//		WorkflowInstance instance = instanceService.findById(task.getWorkflowInstanceId()).get();
//		Assert.assertTrue(instance.getStatus() == WorkflowInstance.INSTANCE_STATUS_END);
//	}
//	
//	@Test
//	public void testReject() {
//		WorkflowTask task = flowAPI.start(projectId, Workflow.FLOW_TYPE_FEE);
//		Assert.assertNotNull(task);
//		
//		flowAPI.reject(task.getId());
//		
//		List<WorkflowTask> tasks = flowAPI.findApproveHistory(projectId, userId1);
//		Assert.assertTrue(tasks.size() == 1);
//		Assert.assertTrue(tasks.get(0).getStatus() == WorkflowTask.TASK_STATUS_ROLLBACK);
//		
//		WorkflowInstance instance = instanceService.findById(task.getWorkflowInstanceId()).get();
//		Assert.assertTrue(instance.getStatus() == WorkflowInstance.INSTANCE_STATUS_END);
//		
//	}
}
