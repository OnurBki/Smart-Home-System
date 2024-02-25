import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator {

    // The date-time formatter used for date-time string parsing and formatting.
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    /**
     * Returns the date-time formatter used for date-time string parsing and formatting.
     * @return the date-time formatter used for date-time string parsing and formatting
     */
    public static DateTimeFormatter getDateTimeFormatter() {return DATE_TIME_FORMATTER;}

    /**
     * Checks if the specified date-time string is valid and can be parsed using the
     * date-time formatter.
     * @param dateStr the date-time string to be validated
     * @return true if the date-time string is valid and can be parsed using the
     *         date-time formatter, false otherwise
     */
    public static boolean isValid(String dateStr) {
        try {
            LocalDateTime.parse(dateStr, DateTimeValidator.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
