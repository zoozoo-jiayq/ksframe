package com.jiayq.ks.app.project;

@Service
@Transaction
public class ProjectService extends BaseServiceProxy<Project>{

	@Resource
	private ProjectDao projectDao;

	pubnlic BaseRepository getBaseRepository(){
		return projectDao;
	}
}