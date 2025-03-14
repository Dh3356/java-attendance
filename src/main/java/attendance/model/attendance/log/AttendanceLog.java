package attendance.model.attendance.log;

import attendance.model.attendance.datetime.AttendanceDateTime;
import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceLog {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    private AttendanceLog(AttendanceDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceLog fromDateTime(
            final LocalDateTime dateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyApplied(
                dateTime,
                campusOperationPolicy
        );

        return new AttendanceLog(
                attendanceDateTime,
                AttendanceStatus.fromAttendanceDateTime(attendanceDateTime)
        );
    }

    // 운영 시간 아닐때 검증하도록(아마 구조 바꿔야 할듯)
    public static AttendanceLog fromAbsenceDate(
            final LocalDate absenceDate,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return new AttendanceLog(
                AttendanceDateTime.policyAppliedWithNullTime(absenceDate, campusOperationPolicy),
                AttendanceStatus.ABSENCE
        );
    }

    public boolean isSameDate(LocalDate date) {
        return attendanceDateTime.isSameDate(date);
    }

    public LocalDate getDate() {
        return attendanceDateTime.getDate();
    }

    public Optional<LocalTime> getTime() {
        return attendanceDateTime.getTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public boolean isLate() {
        return attendanceStatus == AttendanceStatus.LATE;
    }

    public boolean isAbsence() {
        return attendanceStatus == AttendanceStatus.ABSENCE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceLog that = (AttendanceLog) o;
        return Objects.equals(attendanceDateTime, that.attendanceDateTime)
                && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDateTime, attendanceStatus);
    }

    @Override
    public String toString() {
        return "AttendanceLog{" +
                "attendanceDateTime=" + attendanceDateTime +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
