package attendance.model.attendance.log;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceLogDeserializerTest {

    private static final Path FILE_PATH = Path.of("src/test/resources/attendances.csv");

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @BeforeAll
    static void setUp() throws IOException {
        Files.createDirectories(FILE_PATH.getParent());
        Files.writeString(FILE_PATH,
                """
                        nickname,datetime
                        쿠키,2024-12-02 13:40
                        쿠키,2024-12-03 10:40
                        워니,2024-12-02 13:40
                        워니,2024-12-03 10:40
                        """);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @DisplayName("Path 를 받아 해당 Path 의 파일에 있는 데이터를 읽어 CrewAttendanceLog 리스트를 반환한다.")
    @Test
    void deserializeFromCsv() {

        // Given
        final Map<Crew, AttendanceLogs> expected = new HashMap<>();

        expected.put(
                new Crew("쿠키"),
                new AttendanceLogs(
                        List.of(
                                AttendanceLog.fromDateTime(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        campusOperationPolicy
                                ),
                                AttendanceLog.fromDateTime(
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        campusOperationPolicy
                                )
                        )
                )
        );

        expected.put(
                new Crew("워니"),
                new AttendanceLogs(
                        List.of(
                                AttendanceLog.fromDateTime(
                                        LocalDateTime.of(2024, 12, 2, 13, 40),
                                        campusOperationPolicy
                                ),
                                AttendanceLog.fromDateTime(
                                        LocalDateTime.of(2024, 12, 3, 10, 40),
                                        campusOperationPolicy
                                )
                        )
                )
        );

        // When
        final Map<Crew, AttendanceLogs> actual = new CrewAttendanceLogDeserializer().deserializeFromCsv(FILE_PATH,
                campusOperationPolicy);

        // Then
        assertThat(actual).containsAllEntriesOf(expected);
    }
}
