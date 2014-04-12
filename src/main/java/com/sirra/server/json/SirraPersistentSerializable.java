package com.sirra.server.json;

/**
 * Use this for fields in entity objects.
 * 
 * @author aris
 */
public interface SirraPersistentSerializable extends SirraSerializable {

	public boolean getIsDirty();
	
	public void setIsClean();
}
