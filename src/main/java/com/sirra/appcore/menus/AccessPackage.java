package com.sirra.appcore.menus;

import java.util.*;

public class AccessPackage {
	protected Set<String> roles;
	protected Set<String> tags;
	protected Set<String> menus;
	
	public AccessPackage(String... roleArray) {
		roles = new HashSet();
		tags = new HashSet();
		menus = new HashSet();
		
		for(String role: roleArray) {
			roles.add(role);
		}
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public void addMenu(String metaId) {
		menus.add(metaId);
	}
	
	public boolean isApplicable(String roleMetaId) {
		if(roles.contains("all")) return true;
		return roles.contains(roleMetaId);
	}
	
	public Set<String> getTags() {
		return tags;
	}
	
	public Set<String> getMenus() {
		return menus;
	}
}