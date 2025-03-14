package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceLogResponse {

    private final LocalDate date;
    private final LocalTime time;
    private final String attendanceStatus;

    private AttendanceLogResponse(final LocalDate date, final LocalTime time, final String attendanceStatus) {
        this.date = date;
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceLogResponse fromDateTime(final LocalDateTime dateTime) {
        return new AttendanceLogResponse(
                dateTime.toLocalDate(),
                dateTime.toLocalTime(),
                AttendanceStatus.fromDateTime(dateTime).getName()
        );
    }

    public static AttendanceLogResponse fromAbsenceDate(final LocalDate date) {
        return new AttendanceLogResponse(
                date,
                null,
                AttendanceStatus.ABSENCE.getName()
        );
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<LocalTime> getTime() {
        return Optional.ofNullable(time);
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
