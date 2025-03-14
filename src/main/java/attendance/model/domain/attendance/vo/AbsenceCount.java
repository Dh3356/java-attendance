package attendance.model.domain.attendance.vo;

import attendance.model.Calendar;
import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AbsenceCount {

    private final int value;

    private AbsenceCount(final int value) {
        validate(value);
        this.value = value;
    }

    public static AbsenceCount fromDateTimes(final List<LocalDateTime> dateTimes) {
        final long existingDateCount = dateTimes.stream()
                .filter(AttendanceStatus::isAbsence)
                .count();

        final int notExistingDateCount =
                Calendar.getNotExistsDatesCountBeforeToday(convertToDateTimesToDates(dateTimes));

        return new AbsenceCount(Math.toIntExact(existingDateCount + notExistingDateCount));
    }

    private static List<LocalDate> convertToDateTimesToDates(final List<LocalDateTime> dateTimes) {
        return dateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .toList();
    }

    private void validate(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("결석 횟수는 음수가 될 수 없습니다.");
        }
    }

    public int getPolicyAppliedValue(final LateCount lateCount) {
        return value + lateCount.calculatePolicyAppliedAbsenceCount();
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbsenceCount that = (AbsenceCount) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "AbsenceCount{" +
                "value=" + value +
                '}';
    }
}
