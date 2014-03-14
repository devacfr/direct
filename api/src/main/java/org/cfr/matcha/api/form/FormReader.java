package org.cfr.matcha.api.form;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Form reader.
 * 
 */
public class FormReader {

    private static Logger logger = LoggerFactory.getLogger(FormReader.class);

    /** The encoding to use, decoding is enabled, see {@link #decoding}. */
    private volatile String characterSet;

    /** Indicates if the parameters should be decoded. */
    private volatile boolean decoding;

    /** The form stream. */
    private volatile InputStream stream;

    /** The separator character used between parameters. */
    private volatile char separator;

    /**
     * Constructor. Will leave the parsed data encoded.
     * 
     * @param parametersString
     *            The parameters string.
     */
    public FormReader(String parametersString, char separator) {
        this.decoding = false;
        this.stream = new ByteArrayInputStream(parametersString.getBytes());
        this.characterSet = null;
        this.separator = separator;
    }

    /**
     * Constructor.
     * 
     * @param parametersString
     *            The parameters string.
     * @param characterSet
     *            The supported character encoding. Set to null to leave the
     *            data encoded.
     */
    public FormReader(String parametersString, String characterSet, char separator) {
        this.decoding = true;
        this.stream = new ByteArrayInputStream(parametersString.getBytes());
        this.characterSet = characterSet;
        this.separator = separator;
    }

    /**
     * Adds the parameters into a given form.
     * 
     * @param form
     *            The target form.
     */
    public void addParameters(Form form) {
        boolean readNext = true;
        Parameter param = null;

        if (this.stream != null) {
            // Let's read all form parameters
            try {
                while (readNext) {
                    param = readNextParameter();

                    if (param != null) {
                        // Add parsed parameter to the form
                        form.add(param);
                    } else {
                        // Last parameter parsed
                        readNext = false;
                    }
                }
            } catch (IOException ioe) {
                logger.warn("Unable to parse a form parameter. Skipping the remaining parameters.", ioe);
            }

            try {
                this.stream.close();
            } catch (IOException ioe) {
                logger.warn("Unable to close the form input stream", ioe);
            }
        }
    }

    /**
     * Reads all the parameters.
     * 
     * @return The form read.
     * @throws IOException
     *             If the parameters could not be read.
     */
    public Form read() throws IOException {
        final Form result = new Form();
        Parameter param = readNextParameter();

        while (param != null) {
            result.add(param);
            param = readNextParameter();
        }

        this.stream.close();
        return result;
    }

    /**
     * Reads the first parameter with the given name.
     * 
     * @param name
     *            The parameter name to match.
     * @return The parameter value.
     * @throws IOException
     */
    public Parameter readFirstParameter(String name) throws IOException {
        Parameter param = readNextParameter();
        Parameter result = null;

        while ((param != null) && (result == null)) {
            if (param.getName().equals(name)) {
                result = param;
            }

            param = readNextParameter();
        }

        this.stream.close();
        return result;
    }

    /**
     * Reads the next parameter available or null.
     * 
     * @return The next parameter available or null.
     * @throws IOException
     *             If the next parameter could not be read.
     */
    public Parameter readNextParameter() throws IOException {
        Parameter result = null;

        try {
            boolean readingName = true;
            boolean readingValue = false;
            final StringBuilder nameBuffer = new StringBuilder();
            final StringBuilder valueBuffer = new StringBuilder();

            int nextChar = 0;
            while ((result == null) && (nextChar != -1)) {
                nextChar = this.stream.read();

                if (readingName) {
                    if (nextChar == '=') {
                        if (nameBuffer.length() > 0) {
                            readingName = false;
                            readingValue = true;
                        } else {
                            throw new IOException("Empty parameter name detected. Please check your form data");
                        }
                    } else if ((nextChar == this.separator) || (nextChar == -1)) {
                        if (nameBuffer.length() > 0) {
                            result = Parameter.create(nameBuffer, null, this.decoding, this.characterSet);
                        } else if (nextChar == -1) {
                            // Do nothing return null preference
                        } else {
                            logger.info("Empty parameter name detected. Please check your form data");
                        }
                    } else {
                        nameBuffer.append((char) nextChar);
                    }
                } else if (readingValue) {
                    if ((nextChar == this.separator) || (nextChar == -1)) {
                        if (valueBuffer.length() > 0) {
                            result = Parameter.create(nameBuffer, valueBuffer, this.decoding, this.characterSet);
                        } else {
                            result = Parameter.create(nameBuffer, null, this.decoding, this.characterSet);
                        }
                    } else {
                        valueBuffer.append((char) nextChar);
                    }
                }
            }
        } catch (UnsupportedEncodingException uee) {
            throw new IOException("Unsupported encoding. Please contact the administrator");
        }

        return result;
    }

    /**
     * Reads the parameters with the given name. If multiple values are found, a
     * list is returned created.
     * 
     * @param name
     *            The parameter name to match.
     * @return The parameter value or list of values.
     * @throws IOException
     *             If the parameters could not be read.
     */
    @SuppressWarnings("unchecked")
    public Object readParameter(String name) throws IOException {
        Parameter param = readNextParameter();
        Object result = null;

        while (param != null) {
            if (param.getName().equals(name)) {
                if (result != null) {
                    List<Object> values = null;

                    if (result instanceof List) {
                        // Multiple values already found for this parameter
                        values = (List<Object>) result;
                    } else {
                        // Second value found for this parameter
                        // Create a list of values
                        values = new ArrayList<Object>();
                        values.add(result);
                        result = values;
                    }

                    if (param.getValue() == null) {
                        values.add(Parameters.EMPTY_VALUE);
                    } else {
                        values.add(param.getValue());
                    }
                } else {
                    if (param.getValue() == null) {
                        result = Parameters.EMPTY_VALUE;
                    } else {
                        result = param.getValue();
                    }
                }
            }

            param = readNextParameter();
        }

        this.stream.close();
        return result;
    }

    /**
     * Reads the parameters whose name is a key in the given map. If a matching
     * parameter is found, its value is put in the map. If multiple values are
     * found, a list is created and set in the map.
     * 
     * @param parameters
     *            The parameters map controlling the reading.
     * @throws IOException
     *             If the parameters could not be read.
     */
    @SuppressWarnings("unchecked")
    public void readParameters(Map<String, Object> parameters) throws IOException {
        Parameter param = readNextParameter();
        Object currentValue = null;

        while (param != null) {
            if (parameters.containsKey(param.getName())) {
                currentValue = parameters.get(param.getName());

                if (currentValue != null) {
                    List<Object> values = null;

                    if (currentValue instanceof List) {
                        // Multiple values already found for this parameter
                        values = (List<Object>) currentValue;
                    } else {
                        // Second value found for this parameter
                        // Create a list of values
                        values = new ArrayList<Object>();
                        values.add(currentValue);
                        parameters.put(param.getName(), values);
                    }

                    if (param.getValue() == null) {
                        values.add(Parameters.EMPTY_VALUE);
                    } else {
                        values.add(param.getValue());
                    }
                } else {
                    if (param.getValue() == null) {
                        parameters.put(param.getName(), Parameters.EMPTY_VALUE);
                    } else {
                        parameters.put(param.getName(), param.getValue());
                    }
                }
            }

            param = readNextParameter();
        }

        this.stream.close();
    }
}
