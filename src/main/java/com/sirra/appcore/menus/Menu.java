package com.sirra.appcore.menus;

public class Menu {
	protected String metaId;
	protected String name;
	protected String jsClass;
	
	public Menu(String metaId, String name, String jsClass) {
		this.metaId = metaId;
		this.name = name;
		this.jsClass = jsClass;
	}
	
	public String getMetaId() {
		return metaId;
	}
	public void setMetaId(String metaId) {
		this.metaId = metaId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getJsClass() {
		return jsClass;
	}
	public void setJsClass(String jsClass) {
		this.jsClass = jsClass;
	}
}
