package com.jiayq.ks._frame.security;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface LoginUserDao extends BaseRepository<LoginUser> {
	
	public LoginUser findOneByUsername(String username);
	
	@Query(value="select l.* from tb_login_user l left join tb_project_user p on l.id = p.user_id where p.project_id = :projectId",nativeQuery = true)
	public Page<LoginUser> findByProjectId(@Param("projectId")String projectId,Pageable page);
	
	public List<LoginUser> findByPhone(String phone);
	
	public List<LoginUser> findByUsername(String username);
	
	public List<LoginUser> findByNickName(String nickName);
}
