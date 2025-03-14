package attendance.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

public enum Calendar {

    MONDAY(List.of(2, 9, 16, 23, 30)),
    WEEKDAY(List.of(2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 23, 24, 26, 27, 30, 31)),
    WEEKEND(List.of(1, 7, 8, 14, 15, 21, 22, 28, 29)),
    HOLIDAY(List.of(25));

    public static final int NOW_YEAR = 2024;
    public static final int NOW_MONTH = 12;
    public static final int NOW_DAY = 14;
    public static final LocalDate TODAY = LocalDate.of(NOW_YEAR, NOW_MONTH, NOW_DAY);
    private final List<Integer> days;

    Calendar(final List<Integer> days) {
        this.days = days;
    }

    public static List<LocalDate> getNotExistsDatesBeforeToday(final List<LocalDate> dates) {
        final Set<LocalDate> datesSet = Set.copyOf(dates);

        return getDatesBefore(Calendar.TODAY).stream()
                .filter(date -> !datesSet.contains(date))
                .toList();
    }

    public static int getNotExistsDatesCountBeforeToday(final List<LocalDate> dates) {
        return getNotExistsDatesBeforeToday(dates).size();
    }

    private static List<LocalDate> getDatesBefore(final LocalDate date) {
        return WEEKDAY.getDays().stream()
                .filter(result -> result.isBefore(date))
                .toList();
    }

    public static boolean isMonday(final LocalDate date) {
        if (date.getYear() != NOW_YEAR || date.getMonth() != Month.of(NOW_MONTH)) {
            throw new IllegalArgumentException("해당 날짜는 2024년 12월에 포함되지 않습니다.");
        }
        return MONDAY.days.contains(date.getDayOfMonth());
    }

    public List<LocalDate> getDays() {
        return days.stream()
                .map(day -> LocalDate.of(NOW_YEAR, NOW_MONTH, day))
                .toList();
    }
}
