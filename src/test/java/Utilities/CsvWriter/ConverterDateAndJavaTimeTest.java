package Utilities.CsvWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import org.junit.Test;

public class ConverterDateAndJavaTimeTest {
    private static final Locale ERROR_LOCALE = Locale.getDefault();
    private static final String LOCALE = ERROR_LOCALE.toString();
    private static String DEFAULT_FORMAT;

    static {
        try {
            DEFAULT_FORMAT = (String) CsvDate.class.getMethod("value").getDefaultValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void convertToRead_When_ValueIsBlank_Expect_ToReceiveNull() throws Exception {
        assertNull(converter(LocalDate.class, DEFAULT_FORMAT).convertToRead(""));
    }

    @Test
    public void convertToRead_When_ValueIsValidAndTypeIsLocalDate_Expect_ToReceiveLocalDate() throws Exception {
        LocalDate actual = (LocalDate) converter(LocalDate.class, DEFAULT_FORMAT).convertToRead("20190214");
        assertNotNull(actual);
        assertEquals(2019, actual.getYear());
        assertEquals(2, actual.getMonthValue());
        assertEquals(14, actual.getDayOfMonth());
    }

    @Test
    public void convertToRead_When_ValueIsValidAndTypeIsLocalTime_Expect_ToReceiveLocalTime() throws Exception {
        LocalTime actual = (LocalTime) converter(LocalTime.class, DEFAULT_FORMAT).convertToRead("221546");
        assertNotNull(actual);
        assertEquals(22, actual.getHour());
        assertEquals(15, actual.getMinute());
        assertEquals(46, actual.getSecond());
    }

    @Test
    public void convertToRead_When_ValueIsValidAndTypeIsLocalDateTime_Expect_ToReceiveLocalDateTime() throws Exception {
        LocalDateTime actual = (LocalDateTime) converter(LocalDateTime.class, DEFAULT_FORMAT).convertToRead("20190214T221546");
        assertNotNull(actual);
        assertEquals(2019, actual.getYear());
        assertEquals(2, actual.getMonthValue());
        assertEquals(14, actual.getDayOfMonth());
        assertEquals(22, actual.getHour());
        assertEquals(15, actual.getMinute());
        assertEquals(46, actual.getSecond());
    }

    @Test
    public void convertToWrite_When_ValueIsLocalDate_Expect_ToReceiveFormattedDate() throws Exception {
        String actual = converter(LocalDate.class, DEFAULT_FORMAT).convertToWrite(LocalDate.of(2019, 2, 14));
        assertEquals("20190214", actual);
    }

    @Test
    public void convertToWrite_When_ValueIsLocalTime_Expect_ToReceiveFormattedTime() throws Exception {
        String actual = converter(LocalTime.class, DEFAULT_FORMAT).convertToWrite(LocalTime.of(22, 15, 46));
        assertEquals("221546", actual);
    }

    @Test
    public void convertToWrite_When_ValueIsLocalDateTime_Expect_ToReceiveFormattedDateTime() throws Exception {
        String actual = converter(LocalDateTime.class, DEFAULT_FORMAT).convertToWrite(LocalDateTime.of(2019, 2, 14,22, 15, 46));
        assertEquals("20190214T221546", actual);
    }

    private CsvConverter converter(Class type, String format) {
        return new ConverterDateAndJavaTime(type, LOCALE, ERROR_LOCALE, format);
    }
}