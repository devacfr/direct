package org.cfr.direct.rs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.cfr.direct.config.DirectContext;
import org.cfr.direct.handler.IDirectHandler;
import org.cfr.direct.handler.context.IDirectHandlerContext;
import org.cfr.direct.rs.context.DirectJaxRsHandlerContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.softwarementors.extjs.djn.router.RequestType;

@Path("/directrs")
public class DirectHandlerResource implements InitializingBean {

	/** Action Context  */
	@Autowired(required = true)
	private DirectContext directContext;

	public DirectHandlerResource() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Path path = this.getClass()
				.getAnnotation(Path.class);
		directContext.init("", this.getClass()
				.getSimpleName(), this.getClass()
				.getSimpleName(), path.value());
	}

	/**
	 * JSON method using POST Method
	 * 
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String handleJSONPost(@Context UriInfo uriInfo, String json) {

		return handle(json, uriInfo, RequestType.JSON);
	}

	/**
	 * POLL method using GET Method
	 * 
	 * @param uriInfo
	 * @return
	 */
	@GET
	@Path("poll")
	public String handlePollGet(@Context UriInfo uriInfo) {

		return handle("", uriInfo, RequestType.POLL);
	}

	/**
	 * POLL method using POST Method
	 * 
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Path("poll")
	public String handlePollPost(@Context UriInfo uriInfo) {

		return handle("", uriInfo, RequestType.POLL);
	}

	/**
	 * FileUpload method
	 * 
	 * @param input
	 * @param uriInfo
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String handleFormUrlEncodedPost(@Context UriInfo uriInfo, String input) {

		return handle(input, uriInfo, RequestType.FORM_SIMPLE_POST);
	}

	protected String handle(String input, UriInfo uriInfo, RequestType requestType) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			writer = new PrintWriter(stringWriter);
			IDirectHandlerContext handlerContext = new DirectJaxRsHandlerContext(directContext, requestType, uriInfo.getPath(), reader, writer);
			for (IDirectHandler handler : directContext.getDirectHandlers()) {
				handler.process(handlerContext);
			}
			return stringWriter.toString();
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
		}
	}



	public void setDirectContext(DirectContext directContext) {
		this.directContext = directContext;
	}
}
