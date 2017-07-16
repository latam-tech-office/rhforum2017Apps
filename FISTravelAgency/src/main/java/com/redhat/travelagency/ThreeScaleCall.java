package com.redhat.travelagency;


import threescale.v3.api.*;
import threescale.v3.api.impl.*;

public class ThreeScaleCall {
	
	private String hostname;
	private int port;
	private String user_key;
	private String serviceToken;
	private String serviceId;
	private String app_id;
	private String app_key;
	
	
	public ThreeScaleCall(String hostname, int port, String serviceToken, String serviceId, String user_key) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.serviceToken = serviceToken;
		this.serviceId = serviceId;
		this.user_key = user_key;
	}
	
	public ThreeScaleCall(){
		super();
	}
	
	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getUser_key() {
		return user_key;
	}


	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}


	public String getServiceToken() {
		return serviceToken;
	}


	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getApp_id() {
		return app_id;
	}


	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}


	public String getApp_key() {
		return app_key;
	}


	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}


	public void threeScaleAuthRep(String metric){
		
		ServiceApi serviceApi = ServiceApiDriver.createApi();

		// When connecting to an on-premise instance of the 3scale platform, create the API object for a given host and port:
//		ServiceApi serviceApi = ServiceApiDriver.createApi(config.getHostname(), config.getPort(), true);

		ParameterMap params = new ParameterMap();              // the parameters of your call
		params.add("user_key", getUser_key());               // Add the user key of your application for authorization

		String serviceToken = getServiceToken();                             // Your 3scale service token
		String serviceId = getServiceId();                                // The service id for your user key

		ParameterMap usage = new ParameterMap(); // Add a metric to the call
		usage.add(metric, "1");
		params.add("usage", usage);              // metrics belong inside the usage parameter

		AuthorizeResponse response = null;
		// the 'preferred way' of calling the backend: authrep
		try {
		  response = serviceApi.authrep(serviceToken, serviceId, params);
		  System.out.println("AuthRep on User Key Success: " + response.success());
		  if (response.success() == true) {
		    // your api access got authorized and the  traffic added to 3scale backend
		    System.out.println("Plan: " + response.getPlan());
		  } else {
		    // your api access did not authorized, check why
		    System.out.println("Error: " + response.getErrorCode());
		    System.out.println("Reason: " + response.getReason());
		  }
		} catch (ServerError serverError) {
		  serverError.printStackTrace();
		}

	}

}
