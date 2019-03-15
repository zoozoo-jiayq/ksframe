package com.jiayq.ks.app.feeapply;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class FeeApplyService extends BaseServiceProxy<FeeApply> {

	@Resource
	private FeeApplyDao feeApplyDao;
	
	@Override
	protected BaseRepository<FeeApply> getBaseRepository() {
		// TODO Auto-generated method stub
		return feeApplyDao;
	}

	public Page<FeeApply> findByApply(String applyerId,String projectId,Pageable page){
		return feeApplyDao.findByApplyerIdAndProjectId(applyerId, projectId, page);
	}
	
	public FeeApply findByInstanceId(String instanceId) {
		return feeApplyDao.findOneByWorkflowInstanceId(instanceId);
	}
}
