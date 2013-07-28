package com.sirra.appcore.sql;

import java.lang.reflect.*;
import java.util.*;

import org.hibernate.*;
import org.hibernate.metadata.*;

import com.ming.server.json.*;
import com.ming.server.session.*;

/**
 * Defines the columns in an SqlSearch.
 * 
 * @author aris
 */
public class Columns {
	protected List<String> columns;
	
	public Columns(String... cols) {
		columns = new ArrayList();
		for(String col: cols) {
			columns.add(col);
		}
	}
	
	protected Class entityClass;
	public Columns(Class entityClass) {
		columns = new ArrayList();
		
		this.entityClass = entityClass;
		
		SessionFactory sf = MingSession.get().getHibernateSession().getSessionFactory();
		ClassMetadata classMetadata = sf.getClassMetadata(entityClass);
		String[] propertyNames = classMetadata.getPropertyNames();
		
		columns.add(classMetadata.getIdentifierPropertyName());
		for(String name: propertyNames) {
			columns.add(name);
		}	
	}
	
	public void add(String col) {
		columns.add(col);
	}
	
	public String get(int index) {
		return columns.get(index);
	}
	
	public int size() {
		return columns.size();
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public String getForSelect() {
		StringBuffer colString = new StringBuffer();
		Iterator<String> colIt = getColumns().iterator();
		while(colIt.hasNext()) {
			String col = colIt.next();
			colString.append(col);
			
			if(colIt.hasNext()) colString.append(", ");
		}
		return colString.toString();
	}
	
	public <T> List<T> materialize(List<Data> dataList) {
		List<T> resultList = new ArrayList();
		
		try {
			
			for(Data data: dataList) {
				T object = (T) entityClass.newInstance();
				
				for(String fieldName: getColumns()) {
					try {
						Field field = entityClass.getDeclaredField(fieldName);
						
						Object value = data.get(fieldName);
						field.setAccessible(true);
						field.set(object, value);
					} catch(Exception e) {
						throw new RuntimeException(e);
					}
				}
				
				resultList.add(object);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return resultList;
	}
}