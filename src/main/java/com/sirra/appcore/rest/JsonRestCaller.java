package com.sirra.appcore.rest;

import java.io.*;
import java.util.*;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.json.*;

/**
 * Supports making GET, POST and PUT calls to a REST resource.
 * 
 * @author aris
 */
public class JsonRestCaller {
	
	protected String apiUrl;
	protected String responseBody;
	protected String body = null;
	
	protected String method;
	
	// Use this constructor when you will explicitly call the correct method, or default to POST.
	public JsonRestCaller(String apiUrl) {
		this.apiUrl = apiUrl;
		this.method = "post";
	}
	
	/**
	 * Use this constructor when you want to specify the HTTP method up front.
	 * Method can be "post", "put" or "get".
	 */
	public JsonRestCaller(String apiUrl, String method) {
		this.apiUrl = apiUrl;
		this.method = method.toLowerCase();
	}
	
	/**
	 * You can use JsonUtil.convert(...).toString() to convert a Java object to json.
	 */
	public void setPayload(String jsonString) {
		this.body = jsonString;
	}
	
	public void executeCall()
	throws RestException
	{
		if(method.equals("post")) {
			executePostCall();
		} else if(method.equals("get")) {
			executeGetCall();
		} else if(method.equals("put")) {
			executePutCall();
		} else {
			throw new RuntimeException("Unknown HTTP method:" + method);
		}
	}
	
	public void executeGetCall()
	throws RestException
	{
		System.out.println("Making GET JSON REST API call to " + apiUrl);

		GetMethod getMethod = new GetMethod(apiUrl);
		
		try {
			getMethod.setRequestHeader("Content-type", "application/json");
			
			HttpClient client = new HttpClient();
			int statusCode = client.executeMethod(getMethod);

			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed: " + getMethod.getStatusLine());
			}

			// Read the response body.
			byte[] responseBodyBytes = getMethod.getResponseBody();
			responseBody = new String(responseBodyBytes);
			processResponse();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			System.out.println("Received REST response:");
			System.out.println(responseBody);
			
			if (statusCode != HttpStatus.SC_OK) {
				throw new RestException("Status code not OK: " + statusCode);
			}
			
		} catch(HttpException e) {
			throw new RestException(e);
		} catch(IOException e) {
			throw new RestException(e);
		} finally {
			// Release the connection.
			getMethod.releaseConnection();
		}
	}
	
	public void executePostCall()
	throws RestException
	{
		System.out.println("Making POST JSON REST API call to " + apiUrl);
		PostMethod postMethod = new PostMethod(apiUrl);
		
		_executeCall(postMethod);
	}
	
	public void executePutCall()
	throws RestException
	{
		System.out.println("Making PUT JSON REST API call to " + apiUrl);
		PutMethod putMethod = new PutMethod(apiUrl);
		
		_executeCall(putMethod);
	}
	
	protected void _executeCall(EntityEnclosingMethod method)
	throws RestException
	{
		try {
			System.out.println("Body: " + body);
			method.setRequestBody(body);
			method.setRequestHeader("Content-type", "application/json");
			
			HttpClient client = new HttpClient();
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBodyBytes = method.getResponseBody();
			responseBody = new String(responseBodyBytes);
			processResponse();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			System.out.println("Received REST response:");
			System.out.println(responseBody);
			
			if (statusCode != HttpStatus.SC_OK) {
				throw new RestException("Status code not OK: " + statusCode);
			}
			
		} catch(HttpException e) {
			throw new RestException(e);
		} catch(IOException e) {
			throw new RestException(e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		} 
	}

	public String getStringResponse() {
		return responseBody;
	}
	
	public JSONObject getResponseJsonObject() {
		try {
			JSONObject result = new JSONObject(responseBody);
			return result;
		} catch(JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getString(String attributeName) {
		try {
			JSONObject j = new JSONObject(responseBody);
			return (String) j.get(attributeName);
		} catch(JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void processResponse() {
		
	}
}
