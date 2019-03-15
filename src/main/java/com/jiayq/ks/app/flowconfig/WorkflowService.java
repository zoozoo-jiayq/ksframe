
package com.jiayq.ks.app.flowconfig;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class WorkflowService extends BaseServiceProxy<Workflow> {

	@Resource
	private WorkflowDao workflowDao;
	
	@Override
	protected BaseRepository<Workflow> getBaseRepository() {
		// TODO Auto-generated method stub
		return workflowDao;
	}

	public Page<Workflow> findByProjectId(String projectId,Pageable page){
		return workflowDao.findByProjectId(projectId, page);
	}
	
	public Workflow findByType(String projectId,int type){
		return workflowDao.findOneByTypeAndProjectId(type, projectId);
	}
}
