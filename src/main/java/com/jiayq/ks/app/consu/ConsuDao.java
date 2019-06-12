package com.jiayq.ks.app.consu;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ConsuDao extends BaseRepository<Consu> {
	public Page<Consu> findByNameLike(String name,Pageable page);
	
	@Query(value="select d.* from tb_consu d left join tb_project_consu pp on d.id = pp.consu_id where pp.project_id =:projectId ",nativeQuery = true)
	public Page<Consu> findMyConsu(@Param("projectId")String projectId,Pageable page);
	
	public List<Consu> findByName(String name);
}
