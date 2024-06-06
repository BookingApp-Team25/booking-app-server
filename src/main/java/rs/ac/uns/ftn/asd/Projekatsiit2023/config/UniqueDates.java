package rs.ac.uns.ftn.asd.Projekatsiit2023.config;

import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
public class UniqueDates {

    public static final List<LocalDate> HOLIDAYS = Arrays.asList(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 7, 23),
            LocalDate.of(2024, 4, 8),
            LocalDate.of(2024, 1, 7)

    );
    public static final LocalDate SUMMER_BEGINNING = LocalDate.of(2024,6,20);
    public static final LocalDate SUMMER_ENDING = LocalDate.of(2024,9,22);
    public static final LocalDate WINTER_BEGINNING = LocalDate.of(2024,11,22);
    public static final LocalDate WINTER_ENDING = LocalDate.of(2024,3,20);

    public UniqueDates() {
    }
}
