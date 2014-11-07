/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.matcha.handler.processor.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.cfr.commons.util.Assert;
import org.cfr.matcha.api.form.Form;
import org.cfr.matcha.api.form.Parameter;
import org.cfr.matcha.handler.processor.StandardRequestProcessorBase;

import com.softwarementors.extjs.djn.api.Registry;
import com.softwarementors.extjs.djn.config.GlobalConfiguration;
import com.softwarementors.extjs.djn.router.dispatcher.Dispatcher;
import com.softwarementors.extjs.djn.router.processor.RequestException;
import com.softwarementors.extjs.djn.router.processor.ResponseData;
import com.softwarementors.extjs.djn.router.processor.standard.StandardErrorResponseData;
import com.softwarementors.extjs.djn.router.processor.standard.StandardSuccessResponseData;

public abstract class FormPostRequestProcessorBase extends StandardRequestProcessorBase {

    private static Logger logger = Logger.getLogger(FormPostRequestProcessorBase.class);

    protected FormPostRequestProcessorBase(Registry registry, Dispatcher dispatcher,
                                           GlobalConfiguration globalConfiguration) {
        super(registry, dispatcher, globalConfiguration);
    }

    protected String process(Form formParameters, Map<String, FileItem> fileFields) {
        Assert.notNull(formParameters);
        Assert.notNull(fileFields);

        checkNoMissingParameters(formParameters);
        FormPostRequestData request = createRequestObject(formParameters, fileFields);

        ResponseData response = processRequest(request);
        StringBuilder result = new StringBuilder();
        appendIndividualResponseJsonString(response, result);

        return result.toString();
    }

    private static Parameter getAndRemove(Form keyValues, String key) {
        Parameter result = keyValues.getFirst(key);
        keyValues.remove(key);
        return result;
    }

    private static FormPostRequestData createRequestObject(Form formParameters, Map<String, FileItem> fileFields) {
        Assert.notNull(formParameters);
        Assert.notNull(fileFields);

        Form parameters = new Form(formParameters);

        String type = getAndRemove(parameters, FormPostRequestData.TYPE_ELEMENT).getValue();
        String action = getAndRemove(parameters, FormPostRequestData.ACTION_ELEMENT).getValue();
        String method = getAndRemove(parameters, FormPostRequestData.METHOD_ELEMENT).getValue();
        Long tid = Long.valueOf(Long.parseLong(getAndRemove(parameters, FormPostRequestData.TID_ELEMENT).getValue()));
        boolean isUpload =
                Boolean.parseBoolean(getAndRemove(parameters, FormPostRequestData.UPLOAD_ELEMENT).getValue());

        return new FormPostRequestData(type, action, method, tid, isUpload, parameters, fileFields);
    }

    private static void checkNoMissingParameters(Form parameters) {
        Assert.notNull(parameters);

        List<String> missingParameters = new ArrayList<String>();
        addParameterIfMissing(parameters, FormPostRequestData.ACTION_ELEMENT, missingParameters);
        addParameterIfMissing(parameters, FormPostRequestData.METHOD_ELEMENT, missingParameters);
        addParameterIfMissing(parameters, FormPostRequestData.TYPE_ELEMENT, missingParameters);
        addParameterIfMissing(parameters, FormPostRequestData.TID_ELEMENT, missingParameters);
        addParameterIfMissing(parameters, FormPostRequestData.UPLOAD_ELEMENT, missingParameters);

        if (!missingParameters.isEmpty()) {
            RequestException ex = RequestException.forFormPostMissingParameters(missingParameters);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private static void addParameterIfMissing(Form parameters, String parameterName, List<String> missingParameters) {
        Assert.notNull(parameters);
        Assert.hasText(parameterName);
        Assert.notNull(missingParameters);

        if (parameters.getFirst(parameterName) == null) {
            missingParameters.add(parameterName);
        }
    }

    private ResponseData processRequest(FormPostRequestData request) {
        Assert.notNull(request);

        try {
            Object[] parameters;
            parameters = new Object[] { request.getFormParameters(), request.getFileFields() };
            Object result = dispatchStandardMethod(request.getAction(), request.getMethod(), parameters);
            StandardSuccessResponseData response =
                    new StandardSuccessResponseData(request.getTid(), request.getAction(), request.getMethod());
            response.setResult(result);
            return response;
        } catch (Exception t) {
            StandardErrorResponseData response = createJsonServerErrorResponse(request, t);
            logger.error("(Controlled) server error: " + t.getMessage() + " for Form Post Method "
                    + request.getFullMethodName(),
                    t);
            return response;
        }
    }

}