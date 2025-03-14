package attendance.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.attendance.datetime.AttendanceDateTime;
import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    private static final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    private static Stream<Arguments> fromTestCases() {
        return Stream.of(
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 2, 13, 5),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.ATTENDANCE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 2, 13, 6),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.LATE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 2, 13, 30),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.LATE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 2, 13, 31),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.ABSENCE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 3, 10, 5),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.ATTENDANCE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 3, 10, 6),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.LATE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 3, 10, 30),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.LATE
                ),
                Arguments.of(
                        AttendanceDateTime.policyApplied(
                                LocalDateTime.of(2024, 12, 3, 10, 31),
                                campusOperationPolicy
                        ),
                        AttendanceStatus.ABSENCE
                )
        );
    }

    @DisplayName("일시에 대한 출석 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("fromTestCases")
    void from(final AttendanceDateTime attendanceDateTime, final AttendanceStatus expected) {

        // When
        final AttendanceStatus actual = AttendanceStatus.fromAttendanceDateTime(attendanceDateTime);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
