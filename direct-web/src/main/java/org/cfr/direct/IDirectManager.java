package org.cfr.direct;


public interface IDirectManager {




	/**
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 
	 * @return
	 */
	String getNamespace();

	/**
	 * 
	 * @param namespace
	 */
	void setNamespace(String namespace);

	/**
	 * 
	 * @return
	 */
	String getProvidersUrl();

	/**
	 * 
	 * @param providersUrl
	 */
	void setProvidersUrl(String providersUrl);


	boolean isDebug();


	void setDebug(boolean debug);


}