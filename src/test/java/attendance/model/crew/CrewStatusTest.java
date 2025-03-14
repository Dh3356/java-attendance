package attendance.model.crew;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CrewStatusTest {

    private static final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    private static Stream<Arguments> fromAttendanceLogs() {
        return Stream.of(
                Arguments.of(
                        AttendanceLogs.fromLocalDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 14, 0),
                                        LocalDateTime.of(2024, 12, 3, 14, 0),
                                        LocalDateTime.of(2024, 12, 4, 14, 0),
                                        LocalDateTime.of(2024, 12, 5, 14, 0),
                                        LocalDateTime.of(2024, 12, 6, 14, 0),
                                        LocalDateTime.of(2024, 12, 9, 14, 0)
                                ),
                                campusOperationPolicy
                        ), CrewStatus.EXPULSION
                ),
                Arguments.of(
                        AttendanceLogs.fromLocalDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 14, 0),
                                        LocalDateTime.of(2024, 12, 3, 14, 0),
                                        LocalDateTime.of(2024, 12, 4, 14, 0),
                                        LocalDateTime.of(2024, 12, 5, 10, 6),
                                        LocalDateTime.of(2024, 12, 6, 10, 6),
                                        LocalDateTime.of(2024, 12, 9, 13, 6),
                                        LocalDateTime.of(2024, 12, 10, 10, 6),
                                        LocalDateTime.of(2024, 12, 11, 10, 6)
                                ),
                                campusOperationPolicy
                        ), CrewStatus.CONSULTATION
                ),
                Arguments.of(
                        AttendanceLogs.fromLocalDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 14, 0),
                                        LocalDateTime.of(2024, 12, 3, 14, 0),
                                        LocalDateTime.of(2024, 12, 4, 14, 0)
                                ),
                                campusOperationPolicy
                        ), CrewStatus.CONSULTATION
                ),
                Arguments.of(
                        AttendanceLogs.fromLocalDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 14, 0),
                                        LocalDateTime.of(2024, 12, 3, 14, 0)
                                ),
                                campusOperationPolicy
                        ), CrewStatus.WARNING
                ),
                Arguments.of(
                        AttendanceLogs.fromLocalDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 0),
                                        LocalDateTime.of(2024, 12, 3, 10, 0),
                                        LocalDateTime.of(2024, 12, 4, 10, 0),
                                        LocalDateTime.of(2024, 12, 5, 14, 0),
                                        LocalDateTime.of(2024, 12, 6, 10, 0)
                                ),
                                campusOperationPolicy
                        ), CrewStatus.NORMAL
                )
        );
    }

    @DisplayName("출석 상태 리스트를 통해 크루 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("fromAttendanceLogs")
    void fromAttendanceLogs(final AttendanceLogs attendanceLogs, final CrewStatus expected) {

        // When
        final CrewStatus actual = CrewStatus.fromAttendanceLogs(attendanceLogs);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
