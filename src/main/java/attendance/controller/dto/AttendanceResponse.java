package attendance.controller.dto;

import attendance.model.attendance.log.AttendanceLog;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceResponse {

    private final LocalDate date;
    private final LocalTime time;
    private final String attendanceStatus;

    public AttendanceResponse(LocalDate date, LocalTime time, String attendanceStatus) {
        this.date = date;
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceResponse fromAttendanceLog(final AttendanceLog attendanceLog) {
        return new AttendanceResponse(
                attendanceLog.getDate(),
                attendanceLog.getTime().isPresent() ? attendanceLog.getTime().get() : null,
                attendanceLog.getAttendanceStatus().getName()
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
