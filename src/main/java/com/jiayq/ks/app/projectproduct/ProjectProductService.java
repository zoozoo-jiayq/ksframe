package com.jiayq.ks.app.projectproduct;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class ProjectProductService extends BaseServiceProxy<ProjectProduct> {

	@Resource
	private ProjectProductDao projectProductDao;
	
	@Override
	protected BaseRepository<ProjectProduct> getBaseRepository() {
		// TODO Auto-generated method stub
		return projectProductDao;
	}

	public ProjectProduct findByProjectIdAndProductId(String projectId,String productId) {
		return projectProductDao.findOneByProjectIdAndProductId(projectId, productId);
	}
}
