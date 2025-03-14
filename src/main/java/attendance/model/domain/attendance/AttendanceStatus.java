package attendance.model.domain.attendance;

import attendance.model.Calendar;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);

    private static final LocalTime WEEKDAY_LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime WEEKDAY_ABSENCE_TIME = LocalTime.of(10, 30);

    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public static AttendanceStatus fromDateTime(final LocalDateTime dateTime) {
        if (isAbsence(dateTime)) {
            return ABSENCE;
        }
        if (isLate(dateTime)) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public static AttendanceStatus fromName(final String name) {
        return Arrays.stream(values())
                .filter(status -> status.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 출석 상태가 없습니다."));
    }

    public static boolean isAbsence(final LocalDateTime dateTime) {
        final LocalTime time = dateTime.toLocalTime();
        if (Calendar.isMonday(dateTime.toLocalDate())) {
            return time.isAfter(MONDAY_ABSENCE_TIME);
        }
        return time.isAfter(WEEKDAY_ABSENCE_TIME);
    }

    public static boolean isLate(final LocalDateTime dateTime) {
        if (Calendar.isMonday(dateTime.toLocalDate())) {
            return isTimeBetween(dateTime.toLocalTime(), MONDAY_LATE_TIME, MONDAY_ABSENCE_TIME.plusMinutes(1));
        }
        return isTimeBetween(dateTime.toLocalTime(), WEEKDAY_LATE_TIME, WEEKDAY_ABSENCE_TIME.plusMinutes(1));
    }

    public static Map<AttendanceStatus, Integer> calculateStatistics(
            final List<AttendanceStatus> attendanceStatuses
    ) {

        return Arrays.stream(values())
                .collect(Collectors.toMap(
                                status -> status,
                                status -> Math.toIntExact(getFrequency(attendanceStatuses, status)),
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        )
                );
    }

    private static boolean isTimeBetween(final LocalTime time, final LocalTime startTime, final LocalTime endTime) {
        return time.isAfter(startTime) && time.isBefore(endTime);
    }

    private static long getFrequency(final List<AttendanceStatus> attendanceStatuses, final AttendanceStatus status) {
        return attendanceStatuses.stream()
                .filter(attendanceStatus -> attendanceStatus.equals(status))
                .count();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AttendanceStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
