package com.jiayq.ks.app.productapply;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProductApplyDao extends BaseRepository<ProductApply> {

	public Page<ProductApply> findByApplyerIdAndProjectId(String applyerId,String projectId,Pageable page);
	
	public List<ProductApply> findByProjectIdAndStatusAndApplyDateBetween(String projctId,int status,Date begin,Date end);
}
