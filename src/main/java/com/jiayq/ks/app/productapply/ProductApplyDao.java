package com.jiayq.ks.app.productapply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProductApplyDao extends BaseRepository<ProductApply> {

	public Page<ProductApply> findByApplyerIdAndProjectId(String applyerId,String projectId,Pageable page);
}
