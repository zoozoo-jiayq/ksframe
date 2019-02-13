package com.jiayq.ks.app.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProjectDao extends BaseRepository<Project>{

	@Query(value="select p.* from tb_project p left join tb_project_user pu on p.id = pu.project_id where pu.user_id =:userId",nativeQuery=true)
	public Page<Project> findMyProject(@Param("userId")String userId,Pageable page);
}
