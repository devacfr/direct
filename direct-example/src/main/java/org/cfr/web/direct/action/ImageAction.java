package org.cfr.web.direct.action;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.cfr.direct.action.IDirectAction;
import org.springframework.stereotype.Component;

import com.softwarementors.extjs.djn.StringBuilderUtils;
import com.softwarementors.extjs.djn.config.annotations.DirectFormPostMethod;

@Component
public class ImageAction implements IDirectAction{




	@DirectFormPostMethod
	public Result writeImage(Map<String, String> formParameters, Map<String, FileItem> fileFields) throws IOException  {
		Result result = new Result();

		StringBuilder fieldNamesAndValues = new StringBuilder("<p>Non file fields:</p>");
		for( Map.Entry<String,String> entry : formParameters.entrySet()) {
			String fieldName = entry.getKey();
			String value = entry.getValue();
			StringBuilderUtils.appendAll( fieldNamesAndValues, "<b>", fieldName, "</b>='",
					value, "'<br>" );
		}

		fieldNamesAndValues.append( "<p></p><p>FILE fields:</p>" );
		for( Map.Entry<String,FileItem> entry : fileFields.entrySet() ) {
			String fieldName = entry.getKey();
			FileItem fileItem = entry.getValue();
			result.fileContents = IOUtils.toString( fileItem.getInputStream() );
			fileItem.getInputStream().close();
			StringBuilderUtils.appendAll( fieldNamesAndValues, "<b>", fieldName, "</b>:" );

			boolean fileChosen = !fileItem.getName().equals("");
			if( fileChosen ) {
				StringBuilderUtils.appendAll( fieldNamesAndValues, " file=", fileItem.getName(),
						" (size=", Long.toString(fileItem.getSize()), ")" );
			}
			else {
				fieldNamesAndValues.append(" --no file was chosen--" );
			}
		}

		result.fieldNamesAndValues = fieldNamesAndValues.toString();
		result.success = true;
		return result;
	}
}
