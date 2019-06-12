package com.jiayq.ks.app.product;


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
public class ProductService extends BaseServiceProxy<Product> {

	@Resource
	private ProductDao productDao;
	
	@Override
	protected BaseRepository<Product> getBaseRepository() {
		// TODO Auto-generated method stub
		return productDao;
	}

	public Page<Product> findByName(String name,Pageable page){
		return productDao.findByNameLike(name, page);
	}
	
	public Page<Product> findMyProduct(String projectId,Pageable page){
		return productDao.findMyProduct(projectId, page);
	}
	
	public List<Product> findByName(String name){
		return productDao.findByName(name);
	}
}
