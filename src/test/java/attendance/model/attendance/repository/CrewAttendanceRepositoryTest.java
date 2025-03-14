package attendance.model.attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.model.attendance.log.AttendanceLog;
import attendance.model.attendance.log.CrewAttendanceLogDeserializer;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceRepositoryTest {

    private static final Path FILE_PATH = Path.of("src/test/resources/attendances.csv");
    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
    private final CrewAttendanceLogDeserializer crewAttendanceLogDeserializer = new CrewAttendanceLogDeserializer();
    private CrewAttendanceRepository crewAttendanceRepository;

    @BeforeAll
    static void setUp() throws IOException {
        Files.createDirectories(FILE_PATH.getParent());
        Files.writeString(FILE_PATH,
                """
                        nickname,datetime
                        쿠키,2024-12-02 13:40
                        쿠키,2024-12-03 10:40
                        쿠키,2024-12-05 10:05
                        워니,2024-12-02 13:40
                        워니,2024-12-03 10:40
                        워니,2024-12-09 10:05
                        """);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @BeforeEach
    void setUpEach() {
        crewAttendanceRepository = new CrewAttendanceRepository(
                crewAttendanceLogDeserializer,
                FILE_PATH,
                campusOperationPolicy
        );
    }

    //    - 크루와 AttendanceLog 을 통해 크루 출석을 추가한다.
    //    - 크루와 LocalDateTime 을 통해 크루 출석을 수정한다.
    //    - 시작 LocalDate 부터 종료 LocalDate 까지 특정 크루의 CrewAttendanceLog 를 반환한다.

    @DisplayName("크루와 AttendanceLog 을 통해 크루 출석을 추가한다.")
    @Test
    void add() {

        // Given
        final Crew crew = new Crew("쿠키");
        final AttendanceLog attendanceLog = AttendanceLog.fromDateTime(
                LocalDateTime.of(2024, 12, 9, 10, 5),
                campusOperationPolicy
        );

        // When
        crewAttendanceRepository.add(crew, attendanceLog);
        boolean contains = crewAttendanceRepository.getCrewAttendanceLogs().get(crew).contains(attendanceLog);

        // Then
        assertThat(contains).isTrue();
    }

    @DisplayName("크루와 LocalDateTime 을 통해 크루 출석을 수정한다.")
    @Test
    void update() {

        // Given
        final Crew crew = new Crew("쿠키");
        final AttendanceLog from = AttendanceLog.fromDateTime(
                LocalDateTime.of(2024, 12, 5, 10, 5),
                campusOperationPolicy
        );
        final AttendanceLog to = AttendanceLog.fromDateTime(
                LocalDateTime.of(2024, 12, 5, 10, 6),
                campusOperationPolicy
        );

        // When
        crewAttendanceRepository.update(crew, from, to);

        // Then
        boolean containsTo = crewAttendanceRepository.getCrewAttendanceLogs().get(crew).contains(to);
        boolean containsFrom = crewAttendanceRepository.getCrewAttendanceLogs().get(crew).contains(from);

        assertAll(
                () -> assertThat(containsTo).isTrue(),
                () -> assertThat(containsFrom).isFalse()
        );
    }

    @DisplayName("시작 LocalDate 부터 종료 LocalDate 까지 특정 크루의 CrewAttendanceLog 를 반환한다.")
    @Test
    void findByCrewBetween() {

        // Given
        final Crew crew = new Crew("쿠키");
        final LocalDate from = LocalDate.of(2024, 12, 2);
        final LocalDate to = LocalDate.of(2024, 12, 5);
        final List<AttendanceLog> expected = List.of(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 40),
                        campusOperationPolicy
                ),
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 3, 10, 40),
                        campusOperationPolicy
                ),
                AttendanceLog.fromAbsenceDate(
                        LocalDate.of(2024, 12, 4),
                        campusOperationPolicy
                ),
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 5, 10, 5),
                        campusOperationPolicy
                )
        );

        // When
        final List<AttendanceLog> actual = crewAttendanceRepository.findByCrewBetween(crew, from, to,
                campusOperationPolicy).values();

        // Then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
