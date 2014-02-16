package com.sirra.appcore.hibernatetypes;

import com.sirra.server.persistence.*;

/**
 * Adds the hibernate custom type mappings for common Sirra types.
 * 
 * @author aris
 */
public class HibernateSirraTypes {

	public static void configure() {
		HibernateStarter.addCustomTypeMappings(
				PlainDateType.INSTANCE, 
				ListType.INSTANCE, 
				SirraSerializableType.INSTANCE, 
				MapType.INSTANCE);
	}
}