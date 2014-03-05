package org.cfr.web.direct.action;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.cfr.matcha.api.direct.DirectAction;
import org.cfr.matcha.api.direct.DirectFormPostMethod;
import org.cfr.matcha.api.direct.IDirectAction;
import org.cfr.matcha.api.form.Form;
import org.springframework.stereotype.Component;

@Component
@DirectAction
public class ImageAction implements IDirectAction {

    @DirectFormPostMethod
    public Result uploadFile(Form formParameters, Map<String, FileItem> fileFields) throws IOException {
        Result result = new Result();

        result.success = true;
        return result;
    }

    public BufferedImage crop(BufferedImage src, Rectangle rect) {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = dest.getGraphics();
        g.drawImage(src, 0, 0, rect.width, rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
        g.dispose();
        return dest;
    }
}
