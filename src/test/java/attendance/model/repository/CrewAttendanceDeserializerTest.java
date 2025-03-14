package attendance.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.domain.crew.Crew;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceDeserializerTest {

    private static final Path FILE_PATH = Path.of("src/test/resources/attendances.csv");
    private static final Map<Crew, List<LocalDateTime>> DATA_IN_FILE = Map.of(
            Crew.fromName("쿠키"), List.of(
                    LocalDateTime.of(2024, 12, 2, 13, 40),
                    LocalDateTime.of(2024, 12, 3, 10, 40)
            ),
            Crew.fromName("워니"), List.of(
                    LocalDateTime.of(2024, 12, 2, 13, 40),
                    LocalDateTime.of(2024, 12, 3, 10, 40)
            )
    );
    private final CrewAttendanceDeserializer deserializer = new CrewAttendanceDeserializer();

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

    @DisplayName("특정 경로의 파일에서 출석 데이터를 읽어온다.")
    @Test
    void deserialize() {
        // When
        final Map<Crew, List<LocalDateTime>> data = deserializer.readAll(FILE_PATH);

        // Then
        assertThat(data).isEqualTo(DATA_IN_FILE);
    }
}
