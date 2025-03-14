package attendance.model.attendance.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceLogsTest {

    private static final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
    private static AttendanceLogs attendanceLogs;

    @BeforeEach
    void setUp() {
        List<AttendanceLog> values = new ArrayList<>();
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 5, 11, 0),
                        campusOperationPolicy
                )
        );
        attendanceLogs = new AttendanceLogs(values);
    }

    @Test
    void getAllAttendanceLogsBetween() {

        // Given
        final LocalDate from = LocalDate.of(2024, 12, 2);
        final LocalDate to = LocalDate.of(2024, 12, 5);
        final List<AttendanceLog> expected = new ArrayList<>();
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromAbsenceDate(
                        LocalDate.of(2024, 12, 3),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 5, 11, 0),
                        campusOperationPolicy
                )
        );

        // When
        final List<AttendanceLog> actual = attendanceLogs.getAllAttendanceLogsBetween(from, to, campusOperationPolicy)
                .values();

        // Then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void calculateAttendanceStatusStatistics() {

        // Given
        final LocalDate from = LocalDate.of(2024, 12, 2);
        final LocalDate to = LocalDate.of(2024, 12, 5);
        final Map<AttendanceStatus, Integer> expected = new LinkedHashMap<>();
        expected.put(AttendanceStatus.ATTENDANCE, 1);
        expected.put(AttendanceStatus.ABSENCE, 2);
        expected.put(AttendanceStatus.LATE, 1);

        // When
        final Map<AttendanceStatus, Integer> actual = attendanceLogs.getAllAttendanceLogsBetween(from, to,
                campusOperationPolicy).calculateAttendanceStatusStatistics();
        // Then
        assertAll(
                () -> assertThat(actual).containsExactlyEntriesOf(expected), // 순서와 값 모두 검증
                () -> assertThat(actual.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1),
                () -> assertThat(actual.get(AttendanceStatus.ABSENCE)).isEqualTo(2),
                () -> assertThat(actual.get(AttendanceStatus.LATE)).isEqualTo(1)
        );
    }

    //     - 총 결석 횟수를 반환한다.
    //    - 총 지각 횟수를 반환한다.
    //    - 정책이 적용된 총 결석 횟수를 반환한다.
    //    - 정책이 적용된 총 지각 횟수를 반환한다.

    @DisplayName("총 결석 횟수를 반환한다.")
    @Test
    void getAbsenceCount() {

        // Given
        final int expected = 1;

        // When
        final int actual = attendanceLogs.getAbsenceCount();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("총 지각 횟수를 반환한다.")
    @Test
    void getLateCount() {

        // Given
        final int expected = 1;

        // When
        final int actual = attendanceLogs.getLateCount();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정책이 적용된 총 결석 횟수를 반환한다.")
    @Test
    void getPolicyAppliedAbsenceCount() {

        // Given
        final AttendanceLogs attendanceLogs = new AttendanceLogs(new ArrayList<>());
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 10),
                        campusOperationPolicy
                )
        );
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 3, 10, 6),
                        campusOperationPolicy
                )
        );
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        final int expected = 1;

        // When
        final int actual = attendanceLogs.getPolicyAppliedAbsenceCount();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정책이 적용된 총 지각 횟수를 반환한다.")
    @Test
    void getPolicyAppliedLateCount() {

        // Given
        final AttendanceLogs attendanceLogs = new AttendanceLogs(new ArrayList<>());
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 10),
                        campusOperationPolicy
                )
        );
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 3, 10, 6),
                        campusOperationPolicy
                )
        );
        attendanceLogs.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        final int expected = 0;

        // When
        final int actual = attendanceLogs.getPolicyAppliedLateCount();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 AttendanceLog 하나를 수정한다.")
    @Test
    void update() {

        // Given
        final AttendanceLog from = AttendanceLog.fromDateTime(
                LocalDateTime.of(2024, 12, 4, 10, 6),
                campusOperationPolicy
        );
        final AttendanceLog to = AttendanceLog.fromDateTime(
                LocalDateTime.of(2024, 12, 4, 10, 7),
                campusOperationPolicy
        );

        // When
        attendanceLogs.update(from, to);

        // Then
        boolean containsTo = attendanceLogs.contains(to);
        boolean containsFrom = attendanceLogs.contains(from);

        assertAll(
                () -> assertThat(containsTo).isTrue(),
                () -> assertThat(containsFrom).isFalse()
        );
    }
}
