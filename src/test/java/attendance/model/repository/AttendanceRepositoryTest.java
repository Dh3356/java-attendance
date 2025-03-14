package attendance.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.domain.crew.Crew;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceRepositoryTest {

    private static final Entry<Crew, List<LocalDateTime>> CREW_ONE = Map.entry(
            Crew.fromName("one"),
            new ArrayList<>()
    );
    private static final Entry<Crew, List<LocalDateTime>> CREW_TWO = Map.entry(
            Crew.fromName("two"),
            new ArrayList<>()
    );
    private static final Map<Crew, List<LocalDateTime>> REPOSITORY_DATA = new HashMap<>();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository(
            new MockedCrewAttendanceDeserializer(), Path.of("")
    );

    private static Stream<Arguments> findByCrewTestCases() {
        return Stream.of(
                Arguments.of(CREW_ONE.getKey(), CREW_ONE.getValue()),
                Arguments.of(CREW_TWO.getKey(), CREW_TWO.getValue())
        );
    }

    @BeforeAll
    static void setUp() {
        CREW_ONE.getValue().add(LocalDateTime.of(2024, 12, 2, 13, 4));
        CREW_ONE.getValue().add(LocalDateTime.of(2024, 12, 3, 10, 5));

        CREW_TWO.getValue().add(LocalDateTime.of(2024, 12, 4, 10, 4));
        CREW_TWO.getValue().add(LocalDateTime.of(2024, 12, 5, 10, 6));

        REPOSITORY_DATA.put(CREW_ONE.getKey(), CREW_ONE.getValue());
        REPOSITORY_DATA.put(CREW_TWO.getKey(), CREW_TWO.getValue());
    }

    private static Stream<Arguments> findByDateTimeTestCases() {
        return Stream.of(
                Arguments.of(CREW_ONE.getKey(), LocalDate.of(2024, 12, 2), true),
                Arguments.of(CREW_ONE.getKey(), LocalDate.of(2024, 12, 3), true),
                Arguments.of(CREW_ONE.getKey(), LocalDate.of(2024, 12, 4), false),
                Arguments.of(CREW_ONE.getKey(), LocalDate.of(2024, 12, 5), false),
                Arguments.of(CREW_TWO.getKey(), LocalDate.of(2024, 12, 4), true),
                Arguments.of(CREW_TWO.getKey(), LocalDate.of(2024, 12, 5), true),
                Arguments.of(CREW_TWO.getKey(), LocalDate.of(2024, 12, 6), false)
        );
    }

    private static Stream<Arguments> findCrewByNameTestCases() {
        return Stream.of(
                Arguments.of("one", true),
                Arguments.of("two", true),
                Arguments.of("three", false)
        );
    }

    @DisplayName("Crew 를 통해 해당 Crew 의 출석 시간들을 반환한다.")
    @ParameterizedTest(name = "Crew: {0}, expected: {1}")
    @MethodSource("findByCrewTestCases")
    void findByCrew(final Crew crew, final List<LocalDateTime> expected) {
        // When
        final List<LocalDateTime> actual = attendanceRepository.findByCrew(crew);

        // Then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @DisplayName("Crew 의 출석을 저장한다.")
    @Test
    void save() {
        // When
        attendanceRepository.save(CREW_ONE.getKey(),
                LocalDateTime.of(2024, 12, 12, 12, 12)
        );

        // Then
        assertThat(REPOSITORY_DATA.get(CREW_ONE.getKey())).contains(
                LocalDateTime.of(2024, 12, 12, 12, 12)
        );

        // Clean up
        REPOSITORY_DATA.get(CREW_ONE.getKey()).remove(
                LocalDateTime.of(2024, 12, 12, 12, 12)
        );
    }

    @DisplayName("특정 Crew 의 존재 여부를 확인한다.")
    @Test
    void contains() {

        // When
        final boolean actual = REPOSITORY_DATA.containsKey(CREW_ONE.getKey());

        // Then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 Crew 의 출석 기록을 변경한다.")
    @Test
    void update() {
        // When
        attendanceRepository.update(
                CREW_ONE.getKey(),
                LocalDateTime.of(2024, 12, 2, 13, 4),
                LocalDateTime.of(2024, 12, 2, 13, 5)
        );

        // Then
        assertThat(REPOSITORY_DATA.get(CREW_ONE.getKey())).contains(
                LocalDateTime.of(2024, 12, 2, 13, 5)
        );

        // Clean up
        REPOSITORY_DATA.get(CREW_ONE.getKey()).remove(
                LocalDateTime.of(2024, 12, 2, 13, 5)
        );
        REPOSITORY_DATA.get(CREW_ONE.getKey()).add(
                LocalDateTime.of(2024, 12, 2, 13, 4)
        );
    }

    @DisplayName("특정 Crew 의 출석 기록을 삭제한다.")
    @Test
    void delete() {
        // When
        attendanceRepository.deleteAttendanceByCrew(
                CREW_ONE.getKey(),
                LocalDateTime.of(2024, 12, 2, 13, 4)
        );

        // Then
        assertThat(REPOSITORY_DATA.get(CREW_ONE.getKey())).doesNotContain(
                LocalDateTime.of(2024, 12, 2, 13, 4)
        );

        // Clean up
        REPOSITORY_DATA.get(CREW_ONE.getKey()).add(
                LocalDateTime.of(2024, 12, 2, 13, 4)
        );
    }

    @DisplayName("특정 Date 의 Crew 의 출석 기록을 조회한다.")
    @ParameterizedTest(name = "crew: {0}, date: {1}, expected: {2}")
    @MethodSource("findByDateTimeTestCases")
    void findByDateTime(final Crew crew, final LocalDate date, final boolean expected) {
        // When
        final Optional<LocalDateTime> actual = attendanceRepository.findDateTimeByCrewAndDate(crew, date);

        // Then
        assertThat(actual.isPresent()).isEqualTo(expected);
    }

    @DisplayName("모든 Crew 를 조회한다.")
    @Test
    void findAll() {
        // When
        final List<Crew> actual = attendanceRepository.findAllCrews();

        // Then
        assertThat(actual).containsExactlyInAnyOrder(
                CREW_ONE.getKey(),
                CREW_TWO.getKey()
        );
    }

    @DisplayName("이름을 통해 Crew 를 조회한다.")
    @ParameterizedTest(name = "name: {0}, crew: {1}, expected: {2}")
    @MethodSource("findCrewByNameTestCases")
    void findCrewByName(final String name, final boolean expected) {
        // When
        final Optional<Crew> actual = attendanceRepository.findCrewByName(name);

        // Then
        assertThat(actual.isPresent()).isEqualTo(expected);
    }


    private static class MockedCrewAttendanceDeserializer extends CrewAttendanceDeserializer {
        @Override
        public Map<Crew, List<LocalDateTime>> readAll(final Path path) {
            return REPOSITORY_DATA;
        }
    }
}
