package Utilities.CsvWriter;

import com.opencsv.bean.ConverterDate;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public class ConverterDateAndJavaTime extends ConverterDate {
    private static String DEFAULT_FORMAT = "yyyyMMdd'T'HHmmss";
    private static String DEFAULT_DATE_ONLY_FORMAT = "yyyyMMdd";
    private static String DEFAULT_TIME_ONLY_FORMAT = "HHmmss";
    private DateTimeFormatter format;
    /**
     * @param type         The type of the field being populated
     * @param formatString The string to use for formatting the date. See
     *                     {@link com.opencsv.bean.CsvDate#value()}
     * @param locale       If not null or empty, specifies the locale used for
     *                     converting locale-specific data types
     * @param errorLocale The locale to use for error messages.
     */
    public ConverterDateAndJavaTime(Class<?> type, String locale, Locale errorLocale, String formatString) {
        super(type, locale, formatString, errorLocale, null, null, null, null);

        // if the type is LocalDate and using the default format, we know it will fail. Lets use just the date portion of the default date format
        if (DEFAULT_FORMAT.equals(formatString) && LocalDate.class.isAssignableFrom(type)) {
            formatString = DEFAULT_DATE_ONLY_FORMAT;
        } else if (DEFAULT_FORMAT.equals(formatString) && LocalTime.class.isAssignableFrom(type)) {
            formatString = DEFAULT_TIME_ONLY_FORMAT;
        }
        if (this.locale != null) {
            format = DateTimeFormatter.ofPattern(formatString, this.locale);
        } else {
            format = DateTimeFormatter.ofPattern(formatString);
        }
    }

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        if (StringUtils.isNotBlank(value) && Temporal.class.isAssignableFrom(type)) {
            return convertToTemporal(value);
        }
        return super.convertToRead(value);
    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        if (value != null && Temporal.class.isAssignableFrom(value.getClass())) {
            return convertFromTemporal((Temporal) value);
        }
        return super.convertToWrite(value);
    }

    private Temporal convertToTemporal(String value) {
        try {
            return (Temporal) type.getMethod("parse", CharSequence.class, DateTimeFormatter.class).invoke(null, value, format);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke the parse method of " + type.getName(), e);
        }
    }

    private String convertFromTemporal(Temporal value) {
        return format.format(value);
    }
}