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
package org.cfr.matcha.direct.spring;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayOutputStream baos;

    private byte[] buffer;

    public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        // Read InputStream and store its content in a buffer.
        InputStream is = req.getInputStream();
        baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int letti;
        while ((letti = is.read(buf)) > 0)
            baos.write(buf, 0, letti);
        buffer = baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        BufferedServletInputStream bsis = null;
        ByteArrayInputStream bais = null;
        try {
            // Generate a new InputStream by stored buffer
            bais = new ByteArrayInputStream(buffer);
            // Istantiate a subclass of ServletInputStream
            // (Only ServletInputStream or subclasses of it are accepted by the servlet engine!)
            bsis = new BufferedServletInputStream(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bsis;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        BufferedReader bsis = null;
        Reader bais = null;
        try {
            // Generate a new InputStream by stored buffer
            bais = new InputStreamReader(new ByteArrayInputStream(buffer));
            // Istantiate a subclass of ServletInputStream
            // (Only ServletInputStream or subclasses of it are accepted by the servlet engine!)
            bsis = new BufferedReader(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bsis;
    }

    public static class BufferedServletInputStream extends ServletInputStream {

        ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return bais.available();
        }

        @Override
        public int read() {
            return bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return bais.read(buf, off, len);
        }

    }

}