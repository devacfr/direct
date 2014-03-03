package org.cfr.direct.handler.processor.form;

import java.io.IOException;
import java.util.List;

public class Form extends Parameters<Parameter> {

    /**
     * Empty constructor.
     */
    public Form() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param initialCapacity
     *            The initial list capacity.
     */
    public Form(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor.
     * 
     * @param list
     *            The delegate list.
     */
    public Form(List<Parameter> list) {
        super(list);
    }

    /**
     * Constructor. Uses UTF-8 as the character set for encoding non-ASCII
     * characters.
     * 
     * @param queryString
     *            The Web form parameters as a string.
     * @throws IOException
     */
    public Form(String queryString) {
        this(queryString, "UTF-8");
    }

    /**
     * Constructor. Uses UTF-8 as the character set for encoding non-ASCII
     * characters.
     * 
     * @param parametersString
     *            The parameters string to parse.
     * @param separator
     *            The separator character to append between parameters.
     * @throws IOException
     */
    public Form(String parametersString, char separator) {
        this(parametersString, "UTF-8", separator);
    }

    /**
     * Constructor.
     * 
     * @param queryString
     *            The Web form parameters as a string.
     * @param characterSet
     *            The supported character encoding.
     * @throws IOException
     */
    public Form(String queryString, String characterSet) {
        this(queryString, characterSet, '&');
    }

    /**
     * Constructor.
     * 
     * @param parametersString
     *            The parameters string to parse.
     * @param characterSet
     *            The supported character encoding.
     * @param separator
     *            The separator character to append between parameters.
     * @throws IOException
     */
    public Form(String parametersString, String characterSet, char separator) {
        parse(this, parametersString, characterSet, true, separator);
    }

    @Override
    public Parameter createParameter(String name, String value) {
        return new Parameter(name, value);
    }

    @Override
    public Parameters<Parameter> createSeries(List<Parameter> delegate) {
        if (delegate != null) {
            return new Form(delegate);
        }

        return new Form();
    }

    /**
     * Encodes the form using the standard URI encoding mechanism and the UTF-8
     * character set.
     * 
     * @return The encoded form.
     * @throws IOException
     */
    public String encode() throws IOException {
        return encode("UTF-8");
    }

    /**
     * URL encodes the form. The '&' character is used as a separator.
     * 
     * @param characterSet
     *            The supported character encoding.
     * @return The encoded form.
     * @throws IOException
     */
    public String encode(String characterSet) throws IOException {
        return encode(characterSet, '&');
    }

    /**
     * URL encodes the form.
     * 
     * @param characterSet
     *            The supported character encoding.
     * @param separator
     *            The separator character to append between parameters.
     * @return The encoded form.
     * @throws IOException
     */
    public String encode(String characterSet, char separator) throws IOException {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                sb.append(separator);
            }
            get(i).encode(sb, characterSet);
        }

        return sb.toString();
    }

    /**
     * Formats the form as a matrix path string. Uses UTF-8 as the character set
     * for encoding non-ASCII characters.
     * 
     * @return The form as a matrix string.
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs
     *      by Tim Berners Lee</a>
     */
    public String getMatrixString() {
        return getMatrixString("UTF-8");
    }

    /**
     * Formats the form as a query string.
     * 
     * @param characterSet
     *            The supported character encoding.
     * @return The form as a matrix string.
     * @see <a href="http://www.w3.org/DesignIssues/MatrixURIs.html">Matrix URIs
     *      by Tim Berners Lee</a>
     */
    public String getMatrixString(String characterSet) {
        try {
            return encode(characterSet, ';');
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Formats the form as a query string. Uses UTF-8 as the character set for
     * encoding non-ASCII characters.
     * 
     * @return The form as a query string.
     */
    public String getQueryString() {
        return getQueryString("UTF-8");
    }

    /**
     * Formats the form as a query string.
     * 
     * @param characterSet
     *            The supported character encoding.
     * @return The form as a query string.
     */
    public String getQueryString(String characterSet) {
        try {
            return encode(characterSet);
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Parses a parameters string into a given form.
     * 
     * @param form
     *            The target form.
     * @param parametersString
     *            The parameters string.
     * @param characterSet
     *            The supported character encoding.
     * @param decode
     *            Indicates if the query parameters should be decoded using the
     *            given character set.
     * @param separator
     *            The separator character to append between parameters.
     */
    public static void parse(Form form, String parametersString, String characterSet, boolean decode, char separator) {
        if ((parametersString != null) && !parametersString.equals("")) {
            FormReader fr = null;

            if (decode) {
                fr = new FormReader(parametersString, characterSet, separator);
            } else {
                fr = new FormReader(parametersString, separator);
            }

            fr.addParameters(form);
        }
    }

}