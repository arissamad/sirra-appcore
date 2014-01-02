package com.sirra.appcore.menus;

import java.util.*;

/**
 * Define the menus in your server bootstrap code.
 * 
 * @author aris
 */
public class MenuSet {
	
	public static MenuSet instance;
	
	public static MenuSet getInstance() {
		if(instance == null) {
			instance = new MenuSet();
		}
		return instance;
	}

	protected List<Menu> menuList;
	protected List<AccessPackage> packageList;
	protected AccessPackage currentPackage;
	
	protected MenuSet() {
		menuList = new ArrayList<Menu>();
		packageList = new ArrayList();
	}
	
	public void addMenu(String metaId, String name, String jsClass, String... roleMetaIds) {
		menuList.add(new Menu(metaId, name, jsClass));
	}
	
	public void setTargetRoles(String... roles) {
		currentPackage = new AccessPackage(roles);
		packageList.add(currentPackage);
	}
	
	public void assignMenus(String... metaIds) {
		for(String metaId: metaIds) currentPackage.addMenu(metaId);
	}
	
	public void assignTags(String... tags) {
		for(String tag: tags) currentPackage.addTag(tag);
	}
	
	public List<Menu> getMenus(String roleMetaId) {
		List<Menu> finalList = new ArrayList();
		
		Set<String> applicableMenus = new HashSet();
		
		for(AccessPackage ap: packageList) {
			if(ap.isApplicable(roleMetaId)) {
				applicableMenus.addAll(ap.getMenus());
			}
		}
		
		for(Menu menu: menuList) {
			if(applicableMenus.contains(menu.getMetaId())) {
				finalList.add(menu);
			}
		}
		
		return finalList;
	}
	
	public List<String> getTags(String roleMetaId) {
		List<String> finalList = new ArrayList();
		
		for(AccessPackage ap: packageList) {
			if(ap.isApplicable(roleMetaId)) {
				finalList.addAll(ap.getTags());
			}
		}

		return finalList;
	}
}