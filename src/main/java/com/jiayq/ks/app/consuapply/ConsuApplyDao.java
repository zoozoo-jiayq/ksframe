package com.jiayq.ks.app.consuapply;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jiayq.ks._frame.base.BaseRepository;

@Repository
public interface ConsuApplyDao extends BaseRepository<ConsuApply> {

	public Page<ConsuApply> findByApplyerIdAndProjectId(String applyerId,String projectId,Pageable page);
	
	public ConsuApply findOneByWorkflowInstanceId(String workflowInstanceId);
	
	@Query(value="select f.* from tb_consu_apply f left join tb_work_flow_instance i on f.workflow_instance_id=i.id where i.`status` = 200 and f.project_id = :projectId and f.apply_date>=:begin and f.apply_date<=:end",nativeQuery=true)
	public List<ConsuApply> findApprovedHistory(@Param("projectId")String projectId,@Param("begin")Date begin,@Param("end")Date end);
}
