package attendance.model.domain.attendance.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LateCountTest {

    private static Stream<Arguments> fromDateTimesTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 2, 10, 31),
                                LocalDateTime.of(2024, 12, 3, 10, 31),
                                LocalDateTime.of(2024, 12, 4, 10, 31),
                                LocalDateTime.of(2024, 12, 5, 10, 29)
                        ),
                        1
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 9, 10, 31),
                                LocalDateTime.of(2024, 12, 10, 10, 31),
                                LocalDateTime.of(2024, 12, 11, 10, 30),
                                LocalDateTime.of(2024, 12, 12, 10, 29)
                        ),
                        2
                )
        );
    }

    private static Stream<Arguments> calculatePolicyAppliedAbsenceCountTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 2, 13, 31),
                                LocalDateTime.of(2024, 12, 3, 10, 6),
                                LocalDateTime.of(2024, 12, 4, 10, 5),
                                LocalDateTime.of(2024, 12, 5, 10, 29)
                        ),
                        0
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 9, 13, 2),
                                LocalDateTime.of(2024, 12, 10, 10, 25),
                                LocalDateTime.of(2024, 12, 11, 10, 30),
                                LocalDateTime.of(2024, 12, 12, 10, 9)
                        ),
                        1
                )
        );
    }

    private static Stream<Arguments> calculatePolicyAppliedValueTestCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 2, 10, 31),
                                LocalDateTime.of(2024, 12, 3, 10, 31),
                                LocalDateTime.of(2024, 12, 4, 10, 31),
                                LocalDateTime.of(2024, 12, 5, 10, 29)
                        ),
                        1
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2024, 12, 9, 10, 31),
                                LocalDateTime.of(2024, 12, 10, 10, 31),
                                LocalDateTime.of(2024, 12, 11, 10, 30),
                                LocalDateTime.of(2024, 12, 12, 10, 29)
                        ),
                        2
                )
        );
    }

    @DisplayName("날짜와 시간 목록을 받아 지각 횟수를 생성한다.")
    @ParameterizedTest(name = "dateTimes={0}, expectedValue={1}")
    @MethodSource("fromDateTimesTestCases")
    void fromDateTimes(final List<LocalDateTime> dateTimes, final int expectedValue) {
        // When
        final int actualValue = LateCount.fromDateTimes(dateTimes).getValue();

        // Then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("정책이 적용된 결석 횟수를 계산한다.")
    @ParameterizedTest(name = "dateTimes={0}, expectedCalculatedPolicyAppliedValue={1}")
    @MethodSource("calculatePolicyAppliedAbsenceCountTestCases")
    void calculatePolicyAppliedAbsenceCount(
            final List<LocalDateTime> dateTimes,
            final int expectedCalculatedPolicyAppliedValue
    ) {

        // Given
        final LateCount lateCount = LateCount.fromDateTimes(dateTimes);

        // When
        final int actualCalculatedPolicyAppliedValue = lateCount.calculatePolicyAppliedAbsenceCount();

        // Then
        assertThat(actualCalculatedPolicyAppliedValue).isEqualTo(expectedCalculatedPolicyAppliedValue);
    }

    @DisplayName("정책이 적용된 지각 횟수를 계산한다.")
    @ParameterizedTest(name = "dateTimes={0}, expectedCalculatedPolicyAppliedValue={1}")
    @MethodSource("calculatePolicyAppliedValueTestCases")
    void calculatePolicyAppliedValue(
            final List<LocalDateTime> dateTimes,
            final int expectedCalculatedPolicyAppliedValue
    ) {

        // Given
        final LateCount lateCount = LateCount.fromDateTimes(dateTimes);

        // When
        final int actualCalculatedPolicyAppliedValue = lateCount.calculatePolicyAppliedValue();

        // Then
        assertThat(actualCalculatedPolicyAppliedValue).isEqualTo(expectedCalculatedPolicyAppliedValue);
    }
}