package attendance.model.campus;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampusOperationPolicyTest {

    private final CampusOperationPolicy policy = new CampusOperationPolicy();

    private static Stream<Arguments> isOpenDateTestCases() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 1), false),
                Arguments.of(LocalDate.of(2024, 12, 2), true),
                Arguments.of(LocalDate.of(2024, 12, 3), true),
                Arguments.of(LocalDate.of(2024, 12, 4), true),
                Arguments.of(LocalDate.of(2024, 12, 5), true),
                Arguments.of(LocalDate.of(2024, 12, 6), true),
                Arguments.of(LocalDate.of(2024, 12, 7), false)
        );
    }

    private static Stream<Arguments> isOpenTimeTestCases() {
        return Stream.of(
                Arguments.of(LocalTime.of(7, 59), false),
                Arguments.of(LocalTime.of(8, 0), true),
                Arguments.of(LocalTime.of(8, 1), true),
                Arguments.of(LocalTime.of(22, 59), true),
                Arguments.of(LocalTime.of(23, 0), true),
                Arguments.of(LocalTime.of(23, 1), false)
        );
    }

    @DisplayName("특정 날짜가 캠퍼스가 열려있는 날짜인지 확인한다.")
    @ParameterizedTest
    @MethodSource("isOpenDateTestCases")
    void isOpenDate(final LocalDate date, final boolean expected) {

        // When
        final boolean actual = policy.isOpenDate(date);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 시간이 캠퍼스가 열려있는 시간인지 확인한다.")
    @ParameterizedTest
    @MethodSource("isOpenTimeTestCases")
    void isOpenTime(final LocalTime time, final boolean expected) {

        // When
        final boolean actual = policy.isOpenTime(time);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
