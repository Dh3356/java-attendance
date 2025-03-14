package attendance;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Holiday {

    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static boolean isHoliday(LocalDate date) {
        return Arrays.stream(values())
                .map(holiday -> holiday.date)
                .collect(Collectors.toSet())
                .contains(date);
    }
}
