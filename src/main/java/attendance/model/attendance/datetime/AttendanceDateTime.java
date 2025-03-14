package attendance.model.attendance.datetime;

import attendance.model.campus.CampusOperationPolicy;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceDateTime {

    private final LocalDate date;
    private final LocalTime time;

    private AttendanceDateTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public static AttendanceDateTime policyApplied(
            final LocalDateTime dateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        validateDate(dateTime.toLocalDate(), campusOperationPolicy);
        validateTime(dateTime.toLocalTime(), campusOperationPolicy);
        return new AttendanceDateTime(
                dateTime.toLocalDate(),
                dateTime.toLocalTime()
        );
    }

    public static AttendanceDateTime policyAppliedWithNullTime(
            final LocalDate date,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        validateDate(date, campusOperationPolicy);
        return new AttendanceDateTime(
                date,
                null
        );
    }

    private static void validateDate(
            final LocalDate date,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        if (!campusOperationPolicy.isOpenDate(date)) {
            throw new IllegalArgumentException("캠퍼스 운영 일자가 아닙니다.");
        }
    }

    private static void validateTime(
            final LocalTime time,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        if (!campusOperationPolicy.isOpenTime(time)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isBeforeTime(final LocalTime time) {
        return this.time.isBefore(time);
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public boolean isNullTime() {
        return time == null;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isSameDayOfWeek(final DayOfWeek dayOfWeek) {
        return date.getDayOfWeek().equals(dayOfWeek);
    }

    public Optional<LocalTime> getTime() {
        return Optional.ofNullable(time);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    @Override
    public String toString() {
        return "AttendanceDateTime{" +
                "date=" + date +
                ", time=" + time +
                '}';
    }
}
