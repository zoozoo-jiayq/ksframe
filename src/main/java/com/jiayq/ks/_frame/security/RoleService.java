
package com.jiayq.ks._frame.security;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jiayq.ks._frame.base.BaseRepository;
import com.jiayq.ks._frame.base.BaseServiceProxy;

@Service
@Transactional
public class RoleService extends BaseServiceProxy<Role> {

	@Resource
	private RoleDao roleDao;
	
	@Override
	protected BaseRepository<Role> getBaseRepository() {
		// TODO Auto-generated method stub
		return roleDao;
	}

	public Role findByCode(String code) {
		return roleDao.findOneByCode(code);
	}
	
	public void initRoleInfo() {
		Role admin = roleDao.findOneByCode(Role.ADMIN);
		if(admin == null) {
			admin = new Role();
			admin.setCode(Role.ADMIN);
			admin.setName("管理员");
			roleDao.save(admin);
		}
		
		Role pm = roleDao.findOneByCode(Role.PM);
		if(pm == null) {
			pm = new Role();
			pm.setCode(Role.PM);
			pm.setName("项目经理");
			roleDao.save(pm);
		}
		
		Role fin = roleDao.findOneByCode(Role.FIN);
		if(fin == null) {
			fin = new Role();
			fin.setCode(Role.FIN);
			fin.setName("财务");
			roleDao.save(fin);
		}
		
		Role bus = roleDao.findOneByCode(Role.BUSINESSER);
		if(bus == null) {
			bus = new Role();
			bus.setCode(Role.BUSINESSER);
			bus.setName("员工");
			roleDao.save(bus);
		}
	}
}
