package org.cfr.matcha.direct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwarementors.extjs.djn.router.RequestType;

public interface IServletDirectFactory extends IDirectFactory {



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