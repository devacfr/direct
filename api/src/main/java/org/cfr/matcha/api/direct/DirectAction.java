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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The <code>DirectAction</code> annotation allows identifying a class supporting Direct API.
 * For example, the following identify <code>TestAction</code> class as a action with same name:
 * <pre>
 *     &#064;Named
 *     <b>&#064;DirectAction</b>
 *     public class TestAction {
 *
 *       <b>&#064;DirectMethod</b>
 *       public String doEcho(String data) {
 *           return data;
 *       }
 *     }
 * </pre>
 * this action contains a method can call directly in your Javascript:
 * <pre>
 * TestAction.doEcho( field.getValue(),function(result, event) {
 *          var transaction = event.getTransaction(), 
 *              content = 
 *                  format('&lt;b&gt;Successful call to {0}.{1} with response:&lt;/b&gt;&lt;pre&gt;{2}&lt;/pre&gt;',
 *                         transaction.action,transaction.method, Ext.encode(result));
 *              updateMain(content);
 * });
 * </pre>
 * </p>
 * @author devacfr
 * @since 1.0
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface DirectAction {

}