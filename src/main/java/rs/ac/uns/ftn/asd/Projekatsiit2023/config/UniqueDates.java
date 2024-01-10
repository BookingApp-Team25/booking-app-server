package rs.ac.uns.ftn.asd.Projekatsiit2023.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Holidays {

    public static final List<LocalDate> HOLIDAYS = Arrays.asList(
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 7, 23),
            LocalDate.of(2022, 4, 8),
            LocalDate.of(2022, 1, 7)

    );

    public Holidays() {
    }
}
