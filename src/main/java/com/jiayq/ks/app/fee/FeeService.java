package com.jiayq.ks.app.fee;

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
public class FeeService extends BaseServiceProxy<Fee> {

	@Resource
	private FeeDao feeDao;
	
	@Override
	protected BaseRepository<Fee> getBaseRepository() {
		// TODO Auto-generated method stub
		return feeDao;
	}
	
	public Page<Fee> findByName(String name,Pageable page){
		return feeDao.findByNameLike(name, page);
	}
	
	public List<Fee> findByName(String name){
		return feeDao.findByName(name);
	}

	public Page<Fee> findMyFee(String projectId,Pageable page){
		
		return feeDao.findProjectFee(projectId, page);
	}
}
