package com.jiayq.ks.app.project;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProjectService {

	@Resource
	private ProjectService projectService;
//	
//	@Test
//	public void testPageable() {
//		Pageable page = PageRequest.of(0, 20);
//		Page<Project> plist = projectService.findMyProject("1", page);
//		Assert.assertTrue(plist.getTotalElements() == 1);
//		Assert.assertTrue(plist.getSize() == 20);
//		Assert.assertTrue(plist.getTotalPages() == 1);
//		Assert.assertTrue(plist.getNumber() == 0);
//		
//		Assert.assertTrue(plist.getContent()!=null);
//		Assert.assertTrue(plist.getContent().size() == 1);
//		
//	}
}
