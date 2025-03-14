package attendance.model.domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    private static Stream<Arguments> fromDateTimesDateTimeTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 31), AttendanceStatus.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 14, 10), AttendanceStatus.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 29), AttendanceStatus.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 0), AttendanceStatus.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceStatus.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 14, 20), AttendanceStatus.ABSENCE)
        );
    }

    private static Stream<Arguments> isAbsenceTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 31), true),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 14, 10), true),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 29), false),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 30), false),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 29), false),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 30), false),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), true),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 14, 20), true)
        );
    }

    private static Stream<Arguments> isLateTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 6), true),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 30), true),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 14, 10), false),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 6), true),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 30), true),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), false),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 14, 20), false)
        );
    }

    @DisplayName("날짜와 시간을 통해 출석 상태를 가져온다.")
    @ParameterizedTest(name = "dateTime: {0}, expected: {1}")
    @MethodSource("fromDateTimesDateTimeTestCases")
    void fromDateTimesDateTime(final LocalDateTime dateTime, final AttendanceStatus expected) {
        // When
        final AttendanceStatus actual = AttendanceStatus.fromDateTime(dateTime);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("결석 여부를 확인한다.")
    @ParameterizedTest(name = "dateTime: {0}, expected: {1}")
    @MethodSource("isAbsenceTestCases")
    void isAbsence(final LocalDateTime dateTime, final boolean expected) {
        // When
        final boolean actual = AttendanceStatus.isAbsence(dateTime);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("지각 여부를 확인한다.")
    @ParameterizedTest(name = "dateTime: {0}, expected: {1}")
    @MethodSource("isLateTestCases")
    void isLate(final LocalDateTime dateTime, final boolean expected) {
        // When
        final boolean actual = AttendanceStatus.isLate(dateTime);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
