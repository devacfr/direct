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
package org.cfr.matcha.api.form;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Form parameter representation.
 * @author devacfr
 * @since 1.0
 */
@ThreadSafe
public class Parameter implements Comparable<Parameter> {

    /**
     * Static logger.
     */
    private static Logger logger = LoggerFactory.getLogger(Parameter.class);

    /** The first object. */
    private volatile String name;

    /** The second object. */
    private volatile String value;

    /**
     * Creates a parameter.
     * 
     * @param name The parameter name buffer.
     * @param value The parameter value buffer (can be null).
     * @param decode If true, the name and values are decoded with the given characterSet,
     * if false, than nothing is decoded.
     * @param characterSet The supported character encoding.
     * @return The created parameter.
     */
    public static Parameter create(@Nonnull final CharSequence name,
                                   @Nonnull final CharSequence value,
                                   @Nonnull final boolean decode,
                                   final String characterSet) {
        Parameter result = null;

        if (name != null) {
            String nameStr;
            if (decode) {
                nameStr = decodeUrl(name.toString(), characterSet);
            } else {
                nameStr = name.toString();
            }
            if (value != null) {
                String valueStr;
                if (decode) {
                    valueStr = decodeUrl(value.toString(), characterSet);
                } else {
                    valueStr = value.toString();
                }
                result = new Parameter(nameStr, valueStr);
            } else {
                result = new Parameter(nameStr, null);
            }
        }
        return result;
    }

    /**
     * Default constructor.
     */
    public Parameter() {
        this(null, null);
    }

    @Override
    public boolean equals(final Object other) {
        boolean result = this == other;

        if (!result
                && other instanceof Parameter) {
            Parameter param = (Parameter) other;

            if (param.getName() == null
                    && getName() == null || getName() != null && getName().equals(param.getName())) {
                result = param.getValue() == null
                        && getValue() == null || getValue() != null && getValue().equals(param.getValue());
            }
        }
        return result;
    }

    /**
     * Returns the name object.
     * 
     * @return The name object.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value object.
     * 
     * @return The value object.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the first object.
     * 
     * @param name
     *            The first object.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the value object.
     * 
     * @param value The value object.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return hashCode(getName(), getValue());
    }

    @Override
    public String toString() {
        return "("
                + getName() + "," + getValue() + ")";
    }

    /**
     * Computes the hash code of a set of objects. Follows the algorithm
     * specified in List.hasCode().
     * 
     * @param objects the objects to compute the hashCode
     * 
     * @return The hash code of a set of objects.
     */
    public static int hashCode(final Object... objects) {
        int result = 1;

        if (objects != null) {
            for (final Object obj : objects) {
                result = 31
                        * result + (obj == null ? 0 : obj.hashCode());
            }
        }

        return result;
    }

    /**
     * Creates a parameter.
     * 
     * @param name The parameter name buffer.
     * @param value The parameter value buffer (can be null).
     * @return The created parameter.
     * @throws IOException
     */
    public static Parameter create(final CharSequence name, final CharSequence value) {
        if (value != null) {
            return new Parameter(name.toString(), value.toString());
        }
        return new Parameter(name.toString(), null);
    }

    /**
     * Preferred constructor.
     * 
     * @param name The name.
     * @param value The value.
     */
    public Parameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Compares this object with the specified object for order.
     * 
     * @param o The object to be compared.
     * @return A negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final Parameter o) {
        return getName().compareTo(o.getName());
    }

    /**
     * Encodes the parameter and appends the result to the given buffer. Uses
     * the standard URI encoding mechanism.
     * 
     * @param buffer The buffer to append.
     * @param characterSet The supported character encoding
     * @throws IOException If a I/O error occurs.
     */
    public void encode(final Appendable buffer, final String characterSet) throws IOException {
        if (getName() != null) {
            buffer.append(encodeUrl(getName(), characterSet));

            if (getValue() != null) {
                buffer.append('=');
                buffer.append(encodeUrl(getValue(), characterSet));
            }
        }
    }

    /**
     * Encodes the parameter using the standard URI encoding mechanism.
     * 
     * @param characterSet The supported character encoding.
     * @return The encoded string.
     * @throws IOException If a I/O error occurs.
     */
    public String encode(final String characterSet) throws IOException {
        final StringBuilder sb = new StringBuilder();
        encode(sb, characterSet);
        return sb.toString();
    }

    /**
     * Decodes a given string using the standard URI encoding mechanism and the
     * UTF-8 character set.
     * 
     * @param toDecode The string to decode.
     * @return The decoded string.
     */
    public static String decode(final String toDecode) {
        String result = null;

        if (toDecode != null) {
            try {
                result = java.net.URLDecoder.decode(toDecode, "UTF-8");
            } catch (UnsupportedEncodingException uee) {
                logger.warn("Unable to decode the string with the UTF-8 character set.", uee);
            }

        }

        return result;
    }

    /**
     * Decodes a given string using the standard URI encoding mechanism. If the
     * provided character set is null, the string is returned but not decoded.
     * <em><strong>Note:</strong> The <a
     * href="http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be
     * used. Not doing so may introduce incompatibilities.</em>
     * 
     * @param toDecode The string to decode.
     * @param characterSet The name of a supported character encoding.
     * @return The decoded string or null if the named character encoding is not
     *         supported.
     */
    public static String decodeUrl(final String toDecode, final String characterSet) {
        String result = null;
        try {
            result = characterSet == null ? toDecode : java.net.URLDecoder.decode(toDecode, characterSet);
        } catch (UnsupportedEncodingException uee) {
            logger.warn("Unable to decode the string with the UTF-8 character set.", uee);
        }

        return result;
    }

    /**
     * Encodes a given string using the standard URI encoding mechanism and the
     * UTF-8 character set.
     * 
     * @param toEncode The string to encode.
     * @return The encoded string.
     */
    public static String encodeUrl(final String toEncode) {
        String result = null;

        if (toEncode != null) {
            try {
                result = java.net.URLEncoder.encode(toEncode, "UTF-8");
            } catch (UnsupportedEncodingException uee) {
                logger.warn("Unable to encode the string with the UTF-8 character set.", uee);
            }

        }

        return result;
    }

    /**
     * Encodes a given string using the standard URI encoding mechanism. If the
     * provided character set is null, the string is returned but not encoded.
     * 
     * <em><strong>Note:</strong> The <a
     * href="http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that UTF-8 should be
     * used. Not doing so may introduce incompatibilities.</em>
     * 
     * @param toEncode The string to encode.
     * @param characterSet The supported character encoding.
     * @return The encoded string or null if the named character encoding is not
     *         supported.
     */
    public static String encodeUrl(final String toEncode, final String characterSet) {
        String result = null;

        try {
            result = characterSet == null ? toEncode : java.net.URLEncoder.encode(toEncode, characterSet);
        } catch (UnsupportedEncodingException uee) {
            logger.warn("Unable to encode the string with the UTF-8 character set.", uee);
        }

        return result;
    }
}
