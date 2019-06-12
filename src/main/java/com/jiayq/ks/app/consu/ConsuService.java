package com.jiayq.ks.app.consu;

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
public class ConsuService extends BaseServiceProxy<Consu> {

	@Resource
	private ConsuDao consuDao;
	
	@Override
	protected BaseRepository<Consu> getBaseRepository() {
		// TODO Auto-generated method stub
		return consuDao;
	}

	public Page<Consu> findByName(String name,Pageable page){
		return consuDao.findByNameLike(name, page);
	}
	
	public Page<Consu> findMyConsu(String projectId,Pageable page){
		return consuDao.findMyConsu(projectId, page);
	}
	
	public List<Consu> findByName(String name){
		return consuDao.findByName(name);
	}
}
