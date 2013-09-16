package org.cfr.direct;

import javax.ws.rs.core.UriInfo;

import com.softwarementors.extjs.djn.router.RequestType;

public interface IJaxRsDirectFactory extends IDirectFactory {



	/**
	 * 
	 * @param input
	 * @param uriInfo
	 * @param requestType
	 * @return
	 */
	String handleProcess(String input, UriInfo uriInfo, RequestType requestType);


	/**
	 * 
	 * @param jsFileName
	 * @param minified
	 * @return
	 */
	String generateSource(String jsFileName, boolean minified);


	/**
	 * 
	 * @return
	 */
	public String getJsApiPath();

	/**
	 * 
	 * @param jsApiPath
	 */
	public  void setJsApiPath(String jsApiPath);




}