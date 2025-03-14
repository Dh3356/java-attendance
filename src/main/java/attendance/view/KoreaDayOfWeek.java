package attendance.view;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum KoreaDayOfWeek {

    MONDAY("월요일", DayOfWeek.MONDAY),
    TUESDAY("화요일", DayOfWeek.TUESDAY),
    WEDNESDAY("수요일", DayOfWeek.WEDNESDAY),
    THURSDAY("목요일", DayOfWeek.THURSDAY),
    FRIDAY("금요일", DayOfWeek.FRIDAY),
    SATURDAY("토요일", DayOfWeek.SATURDAY),
    SUNDAY("일요일", DayOfWeek.SUNDAY);

    private final String name;
    private final DayOfWeek dayOfWeek;

    KoreaDayOfWeek(final String name, final DayOfWeek dayOfWeek) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
    }

    public static KoreaDayOfWeek fromDayOfWeek(final DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(koreaDayOfWeek -> koreaDayOfWeek.dayOfWeek == dayOfWeek)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 요일이 없습니다."));
    }

    public String getName() {
        return name;
    }
}
