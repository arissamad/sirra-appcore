package com.sirra.appcore.firebase;

import com.sirra.server.json.*;
import com.sirra.server.session.*;

import java.util.*;

import com.sirra.appcore.rest.*;
import com.sirra.appcore.util.*;

/**
 * Use this to post data to Firebase.
 * 
 * @author aris
 */
public class Firebase {
	
	/**
	 * [aris 2016-06] Instead of using this, you should include the Firebase maven project
	 * and do something like this:
	 * 
	 * 	Map map = new HashMap();
	 *	map.put("operationType", operationType);
	 *	map.put("progress", progressPercentage);
	 *	
	 *	Firebase firebase = new Firebase("https://" + com.sirra.appcore.firebase.Firebase.getFirebaseInstance() + ".firebaseio.com/live/" + 
	 *			SirraSession.get().getAccountId());
 	 *
	 *	firebase.setValue(map);
	 */
	
	protected static String firebaseInstance;
	
	/**
	 * The first part of the firebase location, i.e. https://<firebaseInstance>.firebaseio.com.
	 * @param theFirebaseInstance
	 */
	public static void setFirebaseInstance(String theFirebaseInstance) {
		firebaseInstance = theFirebaseInstance;
	}
	
	public static String getFirebaseInstance() {
		return firebaseInstance;
	}
	
	protected static Firebase instance;
	public static Firebase getInstance() {
		if(instance == null) {
			instance = new Firebase();
		}
		return instance;
	}
	
	protected Firebase() {
		
	}
	
	/**
	 * @param path As simple as "messages".
	 */
	public void post(String path, Object payload) {
		SirraSession ss = SirraSession.get();
		
		JsonRestCaller caller = new JsonRestCaller("https://" + firebaseInstance + ".firebaseio.com/live/" + ss.getAccountId() + "/" + path + ".json");
		caller.setPayload(JsonUtil.getInstance().convert(payload).toString());
		try {
			caller.executeCall();
		} catch(RestException e) {
			StackTrace.notify(e, "Couldn't put data to firebase path \"" + path + "\" with payload \"" + payload + "\n");
			throw new RuntimeException(e);
		}
	}
	
	public void postWithoutAccount(String path, Object payload) {
		JsonRestCaller caller = new JsonRestCaller("https://" + firebaseInstance + ".firebaseio.com/" + path + ".json");
		caller.setPayload(JsonUtil.getInstance().convert(payload).toString());
		try {
			caller.executeCall();
		} catch(RestException e) {
			StackTrace.notify(e, "Couldn't put data to firebase path \"" + path + "\" with payload \"" + payload + "\n");
			throw new RuntimeException(e);
		}
	}
}
