package com.jiayq.ks.app.projectfee;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class ProjectFeeService extends BaseServiceProxy<ProjectFee> {

	@Resource
	private ProjectFeeDao projectFeeDao;
	
	@Override
	protected BaseRepository<ProjectFee> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectFeeDao;
	}

}
