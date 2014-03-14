package com.sirra.appcore.menus;

import java.util.*;

import com.sirra.appcore.users.*;

public class TagsAccessPackage extends AccessPackage {
	protected Set<String> tags;
	
	public TagsAccessPackage(String... tagsArray) {
		super();
		
		tags = new HashSet();
		
		for(String tag: tagsArray) {
			tags.add(tag);
		}
	}
	
	@Override
	public boolean isApplicable(BaseUser user) {
		if(user.getTags() == null) return false;
		
		for(String tag: user.getTags()) {
			if(tags.contains(tag)) return true;
		}
		
		return false;
	}
}
