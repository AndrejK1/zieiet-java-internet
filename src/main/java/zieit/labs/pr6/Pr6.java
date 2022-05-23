package zieit.labs.pr6;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Pr6 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("E, MMM dd yyyy");

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(FORMATTER.format(localDateTime));

        LocalDate customDate = LocalDate.of(1988, Month.SEPTEMBER, 29);
        System.out.println(CUSTOM_FORMATTER.format(customDate));
    }
}
