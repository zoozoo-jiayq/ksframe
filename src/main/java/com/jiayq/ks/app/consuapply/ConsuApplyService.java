package com.jiayq.ks.app.consuapply;

import java.util.Date;
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
public class ConsuApplyService extends BaseServiceProxy<ConsuApply> {

	@Resource
	private ConsuApplyDao consuApplyDao;
	
	@Override
	protected BaseRepository<ConsuApply> getBaseRepository() {
		// TODO Auto-generated method stub
		return consuApplyDao;
	}

	public Page<ConsuApply> findByApply(String applyerId,String projectId,Pageable page){
		return consuApplyDao.findByApplyerIdAndProjectId(applyerId, projectId, page);
	}
	
	public ConsuApply findByInstanceId(String instanceId) {
		return consuApplyDao.findOneByWorkflowInstanceId(instanceId);
	}
	
	public List<ConsuApply> findApprovedHistory(String projectId,Date begin,Date end){
		return consuApplyDao.findApprovedHistory(projectId, begin, end);
	}
}
