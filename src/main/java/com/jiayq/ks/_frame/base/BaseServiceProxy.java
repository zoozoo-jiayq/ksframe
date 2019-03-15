package com.jiayq.ks._frame.base;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.jiayq.ks._frame.security.UserDetailsAdapter;

public abstract class BaseServiceProxy<T extends BaseModel> implements PagingAndSortingRepository<T, String> {
	
	abstract protected BaseRepository<T> getBaseRepository();

	@Override
	public <S extends T> S save(S entity) {
		// TODO Auto-generated method stub
		if(entity.getInserttime()==null) {
			entity.setInserttime(new Date());
		}
		if(StringUtils.isEmpty(entity.getInsertUserId())) {
			if(SecurityContextHolder.getContext()!=null && SecurityContextHolder.getContext().getAuthentication()!=null && SecurityContextHolder.getContext().getAuthentication().getPrincipal()!=null) {
				UserDetailsAdapter uda =  (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				entity.setInsertUserId(uda.getUser().getId());
			}
		}
		return getBaseRepository().save(entity);
	}

	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return getBaseRepository().saveAll(entities);
	}

	@Override
	public Optional<T> findById(String id) {
		// TODO Auto-generated method stub
		return getBaseRepository().findById(id);
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return getBaseRepository().existsById(id);
	}

	@Override
	public Iterable<T> findAll() {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll();
	}

	@Override
	public Iterable<T> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAllById(ids);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return getBaseRepository().count();
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		getBaseRepository().deleteById(id);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		getBaseRepository().delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
		getBaseRepository().deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		getBaseRepository().deleteAll();
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll(sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll(pageable);
	}

	
	
}
