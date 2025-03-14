package attendance.model.domain.crew;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.domain.attendance.CrewAttendance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DefaultCrewAttendanceComparatorTest {

    private final DefaultCrewAttendanceComparator comparator = new DefaultCrewAttendanceComparator();

    private static Stream<Arguments> compareMoreAbsenceTestCases() {
        return Stream.of(
                Arguments.of(
                        CrewAttendance.of(
                                Crew.fromName("쿠키"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 40),
                                        LocalDateTime.of(2024, 12, 9, 13, 0),
                                        LocalDateTime.of(2024, 12, 10, 10, 0),
                                        LocalDateTime.of(2024, 12, 11, 10, 0),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        ),
                        CrewAttendance.of(
                                Crew.fromName("빙티"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 0),
                                        LocalDateTime.of(2024, 12, 9, 13, 0),
                                        LocalDateTime.of(2024, 12, 10, 10, 0),
                                        LocalDateTime.of(2024, 12, 11, 10, 0),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        )
                )
        );
    }

    private static Stream<Arguments> compareMoreLateTestCases() {
        return Stream.of(
                Arguments.of(
                        CrewAttendance.of(
                                Crew.fromName("쿠키"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 40),
                                        LocalDateTime.of(2024, 12, 9, 13, 20),
                                        LocalDateTime.of(2024, 12, 10, 10, 10),
                                        LocalDateTime.of(2024, 12, 11, 10, 10),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        ),
                        CrewAttendance.of(
                                Crew.fromName("빙티"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 40),
                                        LocalDateTime.of(2024, 12, 9, 13, 20),
                                        LocalDateTime.of(2024, 12, 10, 10, 10),
                                        LocalDateTime.of(2024, 12, 11, 10, 0),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        )
                )
        );
    }

    private static Stream<Arguments> compareMoreNameTestCases() {
        return Stream.of(
                Arguments.of(
                        CrewAttendance.of(
                                Crew.fromName("abc"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 40),
                                        LocalDateTime.of(2024, 12, 9, 13, 10),
                                        LocalDateTime.of(2024, 12, 10, 10, 10),
                                        LocalDateTime.of(2024, 12, 11, 10, 20),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        ),
                        CrewAttendance.of(
                                Crew.fromName("def"),
                                List.of(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        LocalDateTime.of(2024, 12, 4, 10, 40),
                                        LocalDateTime.of(2024, 12, 5, 10, 40),
                                        LocalDateTime.of(2024, 12, 6, 10, 40),
                                        LocalDateTime.of(2024, 12, 9, 13, 10),
                                        LocalDateTime.of(2024, 12, 10, 10, 10),
                                        LocalDateTime.of(2024, 12, 11, 10, 10),
                                        LocalDateTime.of(2024, 12, 12, 10, 0),
                                        LocalDateTime.of(2024, 12, 13, 10, 0)
                                )
                        )
                )
        );
    }

    @DisplayName("결석이 많은 순으로 먼저 정렬한다.")
    @ParameterizedTest(name = "bigger: {0}, smaller: {1}")
    @MethodSource("compareMoreAbsenceTestCases")
    void compareMoreAbsence(final CrewAttendance bigger, final CrewAttendance smaller) {
        // Given
        final List<CrewAttendance> crewAttendances = List.of(smaller, bigger);

        // When
        final List<CrewAttendance> sortedCrewAttendances = crewAttendances.stream()
                .sorted(comparator)
                .toList();

        // Then
        assertThat(sortedCrewAttendances).containsExactly(bigger, smaller);
    }

    @DisplayName("결석이 같다면, 지각이 많은 순으로 정렬한다.")
    @ParameterizedTest(name = "bigger: {0}, smaller: {1}")
    @MethodSource("compareMoreLateTestCases")
    void compareMoreLate(final CrewAttendance bigger, final CrewAttendance smaller) {
        // Given
        final List<CrewAttendance> crewAttendances = List.of(smaller, bigger);

        // When
        final List<CrewAttendance> sortedCrewAttendances = crewAttendances.stream()
                .sorted(comparator)
                .toList();

        // Then
        assertThat(sortedCrewAttendances).containsExactly(bigger, smaller);
    }

    @DisplayName("결석과 지각이 같다면, 이름 순으로 정렬한다.")
    @ParameterizedTest(name = "bigger: {0}, smaller: {1}")
    @MethodSource("compareMoreNameTestCases")
    void compareMoreName(final CrewAttendance bigger, final CrewAttendance smaller) {
        // Given
        final List<CrewAttendance> crewAttendances = List.of(smaller, bigger);

        // When
        final List<CrewAttendance> sortedCrewAttendances = crewAttendances.stream()
                .sorted(comparator)
                .toList();

        // Then
        assertThat(sortedCrewAttendances).containsExactly(bigger, smaller);
    }
}
