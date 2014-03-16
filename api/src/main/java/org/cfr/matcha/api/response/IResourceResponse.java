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
package org.cfr.matcha.api.response;

public interface IResourceResponse {

    /**
     * Set an encoding used for reading/writing the model.
     *
     * @param modelEncoding the encoding used when reading/writing the model.
     */
    void setModelEncoding(String modelEncoding);

    /**
     * @return the current encoding used when reading/writing this model.
     */
    String getModelEncoding();
}
