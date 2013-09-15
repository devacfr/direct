package org.cfr.direct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwarementors.extjs.djn.router.RequestType;

public interface IServletDirectManager extends IDirectManager {



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


	boolean isCreateSourceFiles();


	void setCreateSourceFiles(boolean createSourceFiles);

}