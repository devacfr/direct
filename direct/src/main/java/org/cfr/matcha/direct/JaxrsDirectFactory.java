package org.cfr.matcha.direct;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.cfr.matcha.direct.config.DirectContext;
import org.cfr.matcha.direct.handler.IDirectHandler;
import org.cfr.matcha.direct.handler.context.IDirectHandlerContext;
import org.cfr.matcha.direct.rs.context.DirectJaxRsHandlerContext;

import com.softwarementors.extjs.djn.api.RegisteredApi;
import com.softwarementors.extjs.djn.jscodegen.ApiCodeGenerator;
import com.softwarementors.extjs.djn.jscodegen.Minifier;
import com.softwarementors.extjs.djn.router.RequestType;

public class JaxrsDirectFactory extends BaseDirectFactory implements IJaxRsDirectFactory {



	@Override
	public void init() throws Exception {
		this.setDefaultCreateSourceFiles(false);
		super.init();
	}


	@Override
	public String handleProcess(String input, UriInfo uriInfo, RequestType requestType) {
		DirectContext directContext = getDirectContext();
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


	@Override
	public String getJsApiPath() {
		return getDefaultJsApiPath();
	}

	@Override
	public void setJsApiPath(String jsApiPath) {
		this.setDefaultJsApiPath(jsApiPath);
	}


	@Override
	public String generateSource(String jsFileName, boolean minified) {
		DirectContext directContext = getDirectContext();
		StringBuilder jsBuilder = new StringBuilder();
		for (RegisteredApi api : directContext.getRegistry()
				.getApis()) {
			ApiCodeGenerator generator = new ApiCodeGenerator(directContext.getDirectConfiguration()
					.getGlobalConfiguration(), api);
			generator.appendCode(jsBuilder, minified);

		}
		String jsGenerated = jsBuilder.toString();
		if (minified) {
			jsGenerated = minifiedJsString(jsFileName, jsGenerated);
		}
		return jsGenerated;
	}


	protected String minifiedJsString(String jsFileName, String js) {
		String minified = null;
		try {
			minified = Minifier.minify(js, jsFileName, js.length());
		} catch (Exception e) {
			logger.warn("Minifying failed return not minified JS.", e);
		} finally {
			if (minified == null) {
				minified = js;
			}
		}
		return minified;
	}

}
