package com.jiayq.ks.app.productapply;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;
import com.jiayq.ks.app.Constant;

@Service
@Transactional
public class ProductApplyService extends BaseServiceProxy<ProductApply> {

	@Resource
	private ProductApplyDao productApplyDao;
	
	@Override
	protected BaseRepository<ProductApply> getBaseRepository() {
		// TODO Auto-generated method stub
		return productApplyDao;
	}

	public Page<ProductApply> findMyApply(String userId,String projectId,Pageable page){
		return productApplyDao.findByApplyerIdAndProjectId(userId,projectId, page);
	}
	
	public List<ProductApply> findByProjectIdAndDays(String projectId,Date begin,Date end){
		return productApplyDao.findByProjectIdAndStatusAndApplyDateBetween(projectId, Constant.STATUS_ENABLE, begin, end);
	}
}
