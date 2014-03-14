package com.sirra.appcore.menus;

import java.util.*;

import com.sirra.appcore.users.*;

public class AccessPackage {
	protected Set<String> roles;
	protected Set<String> keys;
	protected Set<String> menus;
	
	public AccessPackage(String... roleArray) {
		roles = new HashSet();
		keys = new HashSet();
		menus = new HashSet();
		
		for(String role: roleArray) {
			roles.add(role);
		}
	}
	
	public void addKey(String tag) {
		keys.add(tag);
	}
	
	public void addMenu(String metaId) {
		menus.add(metaId);
	}
	
	public boolean isApplicable(BaseUser user) {
		String roleMetaId = user.getRoleMetaId();
		
		if(roles.contains("all")) return true;
		return roles.contains(roleMetaId);
	}
	
	public Set<String> getKeys() {
		return keys;
	}
	
	public Set<String> getMenus() {
		return menus;
	}
}