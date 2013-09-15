package org.cfr.direct.handler.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwarementors.extjs.djn.router.RequestType;

public interface IDirectManager {


	/**
	 * 
	 * @throws Exception
	 */
	void init() throws Exception;

	/**
	 * 
	 * @param jsDefaultApiPath
	 * @param namespace
	 * @param name
	 * @param providersUrl
	 * @throws Exception
	 */
	public void init(String jsDefaultApiPath, String namespace, String name, String providersUrl)
			throws Exception;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param type
	 */
	public void handleProcess(HttpServletRequest request, HttpServletResponse response, RequestType type);

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

	/**
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 
	 * @return
	 */
	public String getNamespace();

	/**
	 * 
	 * @param namespace
	 */
	public void setNamespace(String namespace);

	/**
	 * 
	 * @return
	 */
	public String getProvidersUrl();

	/**
	 * 
	 * @param providersUrl
	 */
	public void setProvidersUrl(String providersUrl);

}