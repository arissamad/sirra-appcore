package com.sirra.appcore.sql;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.metadata.*;
import org.hibernate.persister.entity.*;
import org.reflections.*;

import com.sirra.server.json.*;
import com.sirra.server.rest.*;
import com.sirra.server.session.*;

/**
 * Defines the columns in an SqlSearch.
 * 
 * @author aris
 */
public class Columns {
	
	protected String tableName;
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
		
		SessionFactory sf = SirraSession.get().getHibernateSession().getSessionFactory();
		ClassMetadata classMetadata = sf.getClassMetadata(entityClass);
		String[] propertyNames = classMetadata.getPropertyNames();
		
		columns.add(classMetadata.getIdentifierPropertyName());
		for(String name: propertyNames) {
			columns.add(name);
		}
		
		if(classMetadata instanceof SingleTableEntityPersister) {
			SingleTableEntityPersister persister = (SingleTableEntityPersister) classMetadata;
			String dcn = persister.getDiscriminatorColumnName();
			
			if(dcn != null) {
				columns.add(dcn);
			}
			
			tableName = persister.getTableName();
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
			
			if(tableName != null) {
				colString.append(tableName + ".");
			}
			colString.append(col);
			
			if(colIt.hasNext()) colString.append(", ");
		}
		return colString.toString();
	}
	
	public <T> List<T> materialize(List<Data> dataList) {
		List<T> resultList = new ArrayList();
		
		try {
			
			DiscriminatorColumn discriminatorAnnotation = (DiscriminatorColumn) entityClass.getAnnotation(DiscriminatorColumn.class);
			String discriminatorName = null;
			Map<String, Class> subClassLookup = new HashMap();
			
			if(discriminatorAnnotation != null) {
				discriminatorName = discriminatorAnnotation.name();
				
				Reflections reflections = new Reflections(ApiServlet.getAPIPackageBase());
		    	Set classes = reflections.getSubTypesOf(entityClass);
		    	
		    	for(Object classObject: classes) {
		    		Class clazz = (Class) classObject;
		    		
		    		DiscriminatorValue subDiscriminator = (DiscriminatorValue) clazz.getAnnotation(DiscriminatorValue.class);
		    		
		    		subClassLookup.put(subDiscriminator.value(), clazz);
		    	}
			}
			
			for(Data data: dataList) {

				T object;
				
				if(discriminatorName != null) {
					Class subEntityClass = subClassLookup.get(data.get(discriminatorName));
					object = (T) subEntityClass.newInstance();
				} else {
					object = (T) entityClass.newInstance();
				}
				
				for(String fieldName: getColumns()) {
					Object value = data.get(fieldName);
					Setter.set(object, fieldName, value);
				}
				
				resultList.add(object);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return resultList;
	}
}