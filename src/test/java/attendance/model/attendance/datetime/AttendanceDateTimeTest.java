package attendance.model.attendance.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeTest {

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @DisplayName("LocalTime 과 CampusOperationPolicy 를 받아 생성된다.")
    @Test
    void policyApplied() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);

        // When
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyApplied(dateTime, campusOperationPolicy);

        // Then
        assertAll(
                () -> assertThat(attendanceDateTime.getDate()).isEqualTo(dateTime.toLocalDate()),
                () -> assertThat(attendanceDateTime.getTime()).hasValue(dateTime.toLocalTime())
        );
    }

    @DisplayName("LocalDate 와 과 CampusOperationPolicy 만을 받아 시간이 null 인 자신을 생성한다.")
    @Test
    void policyAppliedWithNullTime() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);

        // When
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyAppliedWithNullTime(
                dateTime.toLocalDate(), campusOperationPolicy);

        // Then
        assertAll(
                () -> assertThat(attendanceDateTime.getDate()).isEqualTo(dateTime.toLocalDate()),
                () -> assertThat(attendanceDateTime.getTime()).isNotPresent()
        );
    }

    @DisplayName("LocalTime 을 받아 해당 LocalTime 이 자신의 출석 시간보다 이전인지 반환한다.")
    @Test
    void isBeforeTime() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyApplied(dateTime, campusOperationPolicy);

        // When & Then
        assertThat(attendanceDateTime.isBeforeTime(LocalTime.of(13, 6))).isTrue();
    }

    @DisplayName("LocalDate 를 받아 해당 LocalDate 가 자신의 출석 일자인지 반환한다.")
    @Test
    void isSameDate() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyApplied(dateTime, campusOperationPolicy);

        // When & Then
        assertThat(attendanceDateTime.isSameDate(dateTime.toLocalDate())).isTrue();
    }

    @DisplayName("자신의 출석 시간이 value 가 비어 있는 null 한 시간인지 반환한다.")
    @Test
    void isNullTime() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyAppliedWithNullTime(
                dateTime.toLocalDate(), campusOperationPolicy);

        // When & Then
        assertThat(attendanceDateTime.isNullTime()).isTrue();
    }

    @DisplayName("DayOfWeek 를 받아 해당 요일이 자신의 요일인지 반환한다.")
    @Test
    void isSameDayOfWeek() {
        // Given
        final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.policyApplied(dateTime, campusOperationPolicy);

        // When & Then
        assertThat(attendanceDateTime.isSameDayOfWeek(dateTime.getDayOfWeek())).isTrue();
    }
}
