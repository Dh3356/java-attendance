package attendance.model.attendance.status;

import attendance.model.attendance.datetime.AttendanceDateTime;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENCE("결석"),
    LATE("지각");

    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 6);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 31);
    private static final LocalTime WEEKDAY_LATE_TIME = LocalTime.of(10, 6);
    private static final LocalTime WEEKDAY_ABSENCE_TIME = LocalTime.of(10, 31);

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus fromAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {

        if (attendanceDateTime.isNullTime()) {
            return ABSENCE;
        }
        if (attendanceDateTime.isSameDayOfWeek(DayOfWeek.MONDAY)) {
            return calculate(attendanceDateTime, MONDAY_LATE_TIME, MONDAY_ABSENCE_TIME);
        }
        return calculate(attendanceDateTime, WEEKDAY_LATE_TIME, WEEKDAY_ABSENCE_TIME);
    }

    private static AttendanceStatus calculate(
            final AttendanceDateTime attendanceDateTime,
            final LocalTime lateTime,
            final LocalTime absenceTime
    ) {

        if (attendanceDateTime.isBeforeTime(lateTime)) {
            return ATTENDANCE;
        }
        if (attendanceDateTime.isBeforeTime(absenceTime)) {
            return LATE;
        }
        return ABSENCE;
    }

    public static Map<AttendanceStatus, Integer> getStatistics(final List<AttendanceStatus> attendanceStatuses) {
        return new LinkedHashMap<>() {{
            put(ATTENDANCE, countOccurrence(ATTENDANCE, attendanceStatuses));
            put(ABSENCE, countOccurrence(ABSENCE, attendanceStatuses));
            put(LATE, countOccurrence(LATE, attendanceStatuses));
        }};
    }


    private static int countOccurrence(AttendanceStatus attendanceStatus, List<AttendanceStatus> attendanceStatuses) {
        return Math.toIntExact(
                attendanceStatuses.stream()
                        .filter(attendanceStatus::equals)
                        .count()
        );
    }

    public String getName() {
        return name;
    }
}
