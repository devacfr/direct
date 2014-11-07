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
package org.cfr.web.direct.action;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.fileupload.FileItem;
import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectFormPostMethod;
import org.cfr.matcha.api.form.Form;

@Named
@DirectAction
public class ImageAction {

    @DirectFormPostMethod
    public Result uploadFile(final Form formParameters, final Map<String, FileItem> fileFields) throws IOException {
        Result result = new Result();

        result.success = true;
        return result;
    }

    public BufferedImage crop(final BufferedImage src, final Rectangle rect) {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, rect.width, rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
        g.dispose();
        return dest;
    }
}
