package com.jiayq.ks.app.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ProductDao extends BaseRepository<Product> {
	@Query("select p from Product p where name like :name")
	public Page<Product> findByNameLike(@Param("name")String name,Pageable page);
	
	@Query(value="select d.* from tb_product d left join tb_project_product pp on d.id = pp.product_id where pp.project_id =:projectId ",nativeQuery = true)
	public Page<Product> findMyProduct(@Param("projectId")String projectId,Pageable page);
	
	List<Product> findByName(String name);
}
