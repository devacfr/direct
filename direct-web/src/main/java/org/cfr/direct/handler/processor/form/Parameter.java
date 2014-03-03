package org.cfr.direct.handler.processor.form;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Multi-usage parameter. Note that the name and value properties are thread
 * safe, stored in volatile members.
 * 
 */
public class Parameter implements Comparable<Parameter> {

    private static Logger logger = LoggerFactory.getLogger(Parameter.class);

    /** The first object. */
    private volatile String name;

    /** The second object. */
    private volatile String value;

    /**
     * Creates a parameter.
     * 
     * @param name
     *            The parameter name buffer.
     * @param value
     *            The parameter value buffer (can be null).
     * @param decode
     *            If true, the name and values are decoded with the given
     *            CharacterSet, if false, than nothing is decoded.
     * @param characterSet
     *            The supported character encoding.
     * @return The created parameter.
     */
    public static Parameter create(CharSequence name, CharSequence value, boolean decode, String characterSet) {
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
    public boolean equals(Object other) {
        boolean result = (this == other);

        if (!result && (other instanceof Parameter)) {
            Parameter param = (Parameter) other;

            if (((param.getName() == null) && (getName() == null))
                    || ((getName() != null) && getName().equals(param.getName()))) {
                result = (((param.getValue() == null) && (getValue() == null)) || ((getValue() != null) && getValue().equals(param.getValue())));
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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value object.
     * 
     * @param value
     *            The value object.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return hashCode(getName(), getValue());
    }

    @Override
    public String toString() {
        return "(" + getName() + "," + getValue() + ")";
    }

    /**
     * Computes the hash code of a set of objects. Follows the algorithm
     * specified in List.hasCode().
     * 
     * @param objects
     *            the objects to compute the hashCode
     * 
     * @return The hash code of a set of objects.
     */
    public static int hashCode(Object... objects) {
        int result = 1;

        if (objects != null) {
            for (final Object obj : objects) {
                result = 31 * result + (obj == null ? 0 : obj.hashCode());
            }
        }

        return result;
    }

    /**
     * Creates a parameter.
     * 
     * @param name
     *            The parameter name buffer.
     * @param value
     *            The parameter value buffer (can be null).
     * @return The created parameter.
     * @throws IOException
     */
    public static Parameter create(CharSequence name, CharSequence value) {
        if (value != null) {
            return new Parameter(name.toString(), value.toString());
        }
        return new Parameter(name.toString(), null);
    }

    /**
     * Preferred constructor.
     * 
     * @param name
     *            The name.
     * @param value
     *            The value.
     */
    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Compares this object with the specified object for order.
     * 
     * @param o
     *            The object to be compared.
     * @return A negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Parameter o) {
        return getName().compareTo(o.getName());
    }

    /**
     * Encodes the parameter and appends the result to the given buffer. Uses
     * the standard URI encoding mechanism.
     * 
     * @param buffer
     *            The buffer to append.
     * @param characterSet
     *            The supported character encoding
     * @throws IOException
     */
    public void encode(Appendable buffer, String characterSet) throws IOException {
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
     * @param characterSet
     *            The supported character encoding.
     * @return The encoded string.
     * @throws IOException
     */
    public String encode(String characterSet) throws IOException {
        final StringBuilder sb = new StringBuilder();
        encode(sb, characterSet);
        return sb.toString();
    }

    /**
     * Decodes a given string using the standard URI encoding mechanism and the
     * UTF-8 character set.
     * 
     * @param toDecode
     *            The string to decode.
     * @return The decoded string.
     */
    public static String decode(String toDecode) {
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
     * @param toDecode
     *            The string to decode.
     * @param characterSet
     *            The name of a supported character encoding.
     * @return The decoded string or null if the named character encoding is not
     *         supported.
     */
    public static String decodeUrl(String toDecode, String characterSet) {
        String result = null;
        try {
            result = (characterSet == null) ? toDecode : java.net.URLDecoder.decode(toDecode, characterSet);
        } catch (UnsupportedEncodingException uee) {
            logger.warn("Unable to decode the string with the UTF-8 character set.", uee);
        }

        return result;
    }

    /**
     * Encodes a given string using the standard URI encoding mechanism and the
     * UTF-8 character set.
     * 
     * @param toEncode
     *            The string to encode.
     * @return The encoded string.
     */
    public static String encodeUrl(String toEncode) {
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
     * used. Not doing so may introduce incompatibilites.</em>
     * 
     * @param toEncode
     *            The string to encode.
     * @param characterSet
     *            The supported character encoding.
     * @return The encoded string or null if the named character encoding is not
     *         supported.
     */
    public static String encodeUrl(String toEncode, String characterSet) {
        String result = null;

        try {
            result = (characterSet == null) ? toEncode : java.net.URLEncoder.encode(toEncode, characterSet);
        } catch (UnsupportedEncodingException uee) {
            logger.warn("Unable to encode the string with the UTF-8 character set.", uee);
        }

        return result;
    }
}
