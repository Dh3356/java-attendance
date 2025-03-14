package attendance.model.domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.domain.attendance.vo.WarningCount;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ManagementStatusTest {

    private static Stream<Arguments> fromWarningCountTestCases() {
        return Stream.of(
                Arguments.of(
                        WarningCount.fromDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 2),
                                        LocalDateTime.of(2024, 12, 3, 10, 31),
                                        LocalDateTime.of(2024, 12, 4, 10, 20),
                                        LocalDateTime.of(2024, 12, 5, 10, 20),
                                        LocalDateTime.of(2024, 12, 6, 10, 2),
                                        LocalDateTime.of(2024, 12, 7, 10, 2),
                                        LocalDateTime.of(2024, 12, 8, 10, 2),
                                        LocalDateTime.of(2024, 12, 9, 13, 2),
                                        LocalDateTime.of(2024, 12, 10, 10, 2),
                                        LocalDateTime.of(2024, 12, 11, 10, 2),
                                        LocalDateTime.of(2024, 12, 12, 10, 2),
                                        LocalDateTime.of(2024, 12, 13, 10, 3)

                                )
                        ),
                        ManagementStatus.NONE
                ),
                Arguments.of(
                        WarningCount.fromDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 2),
                                        LocalDateTime.of(2024, 12, 3, 10, 31),
                                        LocalDateTime.of(2024, 12, 4, 10, 20),
                                        LocalDateTime.of(2024, 12, 5, 10, 20),
                                        LocalDateTime.of(2024, 12, 6, 10, 20),
                                        LocalDateTime.of(2024, 12, 7, 10, 2),
                                        LocalDateTime.of(2024, 12, 8, 10, 2),
                                        LocalDateTime.of(2024, 12, 9, 13, 2),
                                        LocalDateTime.of(2024, 12, 10, 10, 2),
                                        LocalDateTime.of(2024, 12, 11, 10, 2),
                                        LocalDateTime.of(2024, 12, 12, 10, 2),
                                        LocalDateTime.of(2024, 12, 13, 10, 3)

                                )
                        ),
                        ManagementStatus.WARNING
                ),
                Arguments.of(
                        WarningCount.fromDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 2),
                                        LocalDateTime.of(2024, 12, 3, 10, 31),
                                        LocalDateTime.of(2024, 12, 4, 10, 20),
                                        LocalDateTime.of(2024, 12, 5, 10, 20),
                                        LocalDateTime.of(2024, 12, 6, 10, 20),
                                        LocalDateTime.of(2024, 12, 7, 10, 20),
                                        LocalDateTime.of(2024, 12, 8, 10, 20),
                                        LocalDateTime.of(2024, 12, 9, 13, 20),
                                        LocalDateTime.of(2024, 12, 10, 10, 2),
                                        LocalDateTime.of(2024, 12, 11, 10, 2),
                                        LocalDateTime.of(2024, 12, 12, 10, 2),
                                        LocalDateTime.of(2024, 12, 13, 10, 3)

                                )
                        ),
                        ManagementStatus.COUNSELING

                ),
                Arguments.of(
                        WarningCount.fromDateTimes(
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 2),
                                        LocalDateTime.of(2024, 12, 3, 10, 31),
                                        LocalDateTime.of(2024, 12, 4, 10, 20),
                                        LocalDateTime.of(2024, 12, 5, 10, 20),
                                        LocalDateTime.of(2024, 12, 6, 10, 20),
                                        LocalDateTime.of(2024, 12, 7, 10, 20),
                                        LocalDateTime.of(2024, 12, 8, 10, 20),
                                        LocalDateTime.of(2024, 12, 9, 13, 20),
                                        LocalDateTime.of(2024, 12, 10, 10, 52),
                                        LocalDateTime.of(2024, 12, 11, 10, 52),
                                        LocalDateTime.of(2024, 12, 12, 10, 3),
                                        LocalDateTime.of(2024, 12, 13, 10, 31)

                                )
                        ),
                        ManagementStatus.EXPULSION

                )
        );
    }


    @DisplayName("경고 횟수에 따른 관리 상태를 가져온다.")
    @ParameterizedTest(name = "warningCount: {0}, expected: {1}")
    @MethodSource("fromWarningCountTestCases")
    void fromWarningCount(final WarningCount warningCount, final ManagementStatus expected) {
        // When
        final ManagementStatus actual = ManagementStatus.fromWarningCount(warningCount);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
