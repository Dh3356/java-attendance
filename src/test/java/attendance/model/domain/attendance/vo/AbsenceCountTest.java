package attendance.model.domain.attendance.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AbsenceCountTest {

    private static Stream<Arguments> fromDateTimesTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 1, 13, 0),
                                LocalDateTime.of(2024, 12, 2, 10, 31),
                                LocalDateTime.of(2024, 12, 3, 10, 31),
                                LocalDateTime.of(2024, 12, 4, 10, 31),
                                LocalDateTime.of(2024, 12, 5, 10, 29)
                        ),
                        9
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 8, 13, 0),
                                LocalDateTime.of(2024, 12, 9, 10, 31),
                                LocalDateTime.of(2024, 12, 10, 10, 31),
                                LocalDateTime.of(2024, 12, 11, 10, 30),
                                LocalDateTime.of(2024, 12, 12, 10, 29)
                        ),
                        8
                )
        );
    }

    private static Stream<Arguments> getPolicyAppliedValueTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 1, 13, 5),
                                LocalDateTime.of(2024, 12, 2, 10, 28),
                                LocalDateTime.of(2024, 12, 3, 10, 31),
                                LocalDateTime.of(2024, 12, 4, 10, 31),
                                LocalDateTime.of(2024, 12, 5, 10, 6)
                        ),
                        9
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 8, 13, 0),
                                LocalDateTime.of(2024, 12, 9, 10, 31),
                                LocalDateTime.of(2024, 12, 10, 10, 4),
                                LocalDateTime.of(2024, 12, 11, 10, 30),
                                LocalDateTime.of(2024, 12, 12, 10, 29)
                        ),
                        7
                )
        );
    }

    @DisplayName("날짜와 시간 목록을 받아 결석 횟수를 생성한다.")
    @ParameterizedTest(name = "dateTimes={0}, expectedValue={1}")
    @MethodSource("fromDateTimesTestCases")
    void fromDateTimes(final List<LocalDateTime> dateTimes, final int expectedValue) {
        // When
        final AbsenceCount actual = AbsenceCount.fromDateTimes(dateTimes);

        // Then
        assertThat(actual.getValue()).isEqualTo(expectedValue);
    }

    @DisplayName("지각, 결석 정책이 적용된 결석 횟수를 반환한다.")
    @ParameterizedTest(name = "dateTimes={0}, expectedValue={1}")
    @MethodSource("getPolicyAppliedValueTestCases")
    void getPolicyAppliedValue(final List<LocalDateTime> dateTimes, final int expectedPolicyAppliedValue) {
        // Given
        final AbsenceCount absenceCount = AbsenceCount.fromDateTimes(dateTimes);
        final LateCount lateCount = LateCount.fromDateTimes(dateTimes);

        // When
        final int actualPolicyAppliedValue = absenceCount.getPolicyAppliedValue(lateCount);

        // Then
        assertThat(actualPolicyAppliedValue).isEqualTo(expectedPolicyAppliedValue);
    }
}