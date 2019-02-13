package com.jiayq.ks.app.fee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface FeeDao extends BaseRepository<Fee> {

	@Query(value="select f.* from tb_fee f left join tb_project_fee pf on f.id = pf.fee_id where pf.project_id =:projectId",nativeQuery = true)
	public Page<Fee> findProjectFee(@Param("projectId")String projectId,Pageable page);
	
	public Page<Fee> findByNameLike(String name,Pageable page);
}
