/**
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
package org.cfr.matcha.api.direct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The <code>DirectFormPostMethod</code> annotation allows identifying a method in action class called while form
 * submit.</p>
 * For example, a submit form action receive the profile of user:
 * <pre>
 *     &#064;Named
 *     &#064;DirectAction
 *     public class Profile {
 *
 *        <b>&#064;DirectFormPostMethod</b>
 *        public JSONFormResponse updateBasicInfo(Form formParameters, Map<String, FileItem> fileFields) {
 *            JSONFormResponse result = new JSONFormResponse();
 *            String email = formParameters.getFirstValue("email");
 *            if (email.equals("aaron@extjs.com")) {
 *                result.addFieldError("email", "already taken");
 *            }
 *             return result;
 *        }
 *     }
 * </pre>
 * The Javascript calling this action:
 * <pre>
 * Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
 * ...
 * Ext.create('Ext.form.Panel', {
        // configs for BasicForm
        api: {
            // The server-side method to call for load() requests
            load: <b>Profile.getBasicInfo</b>,
            // The server-side must mark the submit handler as a 'formHandler'
            submit: <b>Profile.updateBasicInfo</b>
        },
        ...
 * </pre>
 * @author devacfr
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DirectFormPostMethod {

}
