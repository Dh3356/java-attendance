package attendance.model.campus;

import attendance.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class CampusOperationPolicy {

    private static final LocalTime DEFAULT_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_CLOSE_TIME = LocalTime.of(23, 0);

    public boolean isOpenDate(final LocalDate date) {
        return isWeekDay(date) && isNotHoliday(date);
    }

    private boolean isWeekDay(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();

        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean isNotHoliday(final LocalDate date) {
        return !Holiday.isHoliday(date);
    }

    public boolean isOpenTime(final LocalTime time) {
        return time.isAfter(DEFAULT_OPEN_TIME.minusMinutes(1))
                && time.isBefore(DEFAULT_CLOSE_TIME.plusMinutes(1));
    }
}
