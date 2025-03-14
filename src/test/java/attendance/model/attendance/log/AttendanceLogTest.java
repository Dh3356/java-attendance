package attendance.model.attendance.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceLogTest {

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @Test
    void fromAttendanceDateTime() {

        // Given
        final LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        final LocalDate expectedDate = LocalDate.of(2024, 12, 2);
        final LocalTime expectedTime = LocalTime.of(13, 5);
        final AttendanceStatus expectedAttendanceStatus = AttendanceStatus.ATTENDANCE;

        // When
        final AttendanceLog actual = AttendanceLog.fromDateTime(attendanceDateTime, campusOperationPolicy);

        // Then
        assertAll(
                () -> assertThat(actual.getDate()).isEqualTo(expectedDate),
                () -> assertThat(actual.getTime()).contains(expectedTime),
                () -> assertThat(actual.getAttendanceStatus()).isEqualTo(expectedAttendanceStatus)
        );
    }

    @Test
    void fromAbsenceDate() {

        // Given
        final LocalDate absenceDate = LocalDate.of(2024, 12, 2);
        final AttendanceStatus expectedAttendanceStatus = AttendanceStatus.ABSENCE;

        // When
        final AttendanceLog actual = AttendanceLog.fromAbsenceDate(absenceDate, campusOperationPolicy);

        // Then
        assertAll(
                () -> assertThat(actual.getDate()).isEqualTo(absenceDate),
                () -> assertThat(actual.getTime()).isNotPresent(),
                () -> assertThat(actual.getAttendanceStatus()).isEqualTo(expectedAttendanceStatus)
        );
    }
}
