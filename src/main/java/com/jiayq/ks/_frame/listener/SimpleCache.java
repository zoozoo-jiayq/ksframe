package com.jiayq.ks._frame.listener;

import org.apache.tomcat.util.collections.ConcurrentCache;

import com.jiayq.ks._frame.security.Role;

public class SimpleCache {

	private static ConcurrentCache<String, Role> rolesCodesCache = new ConcurrentCache<>(100);
	private static ConcurrentCache<String, Role> rolesIdsCache = new ConcurrentCache<>(100);
	
	public static void cacheRole(Iterable<Role> roles) {
		for(Role r: roles) {
			rolesCodesCache.put(r.getCode(), r);
			rolesIdsCache.put(r.getId(), r);
		}
	}
	
	public static Role getRoleByCode(String code){
		return rolesCodesCache.get(code);
	}
	
	public static Role getRoleById(String id) {
		return rolesIdsCache.get(id);
	}
	
}
