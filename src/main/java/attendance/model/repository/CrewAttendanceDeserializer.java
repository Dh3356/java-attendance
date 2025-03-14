package attendance.model.repository;

import attendance.model.domain.crew.Crew;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class CrewAttendanceDeserializer {

    private static final String CREW_DATETIME_DELIMITER = ",";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Map<Crew, List<LocalDateTime>> readAll(final Path filePath) {
        try (final Stream<String> lines = Files.lines(filePath)) {
            final HashMap<Crew, List<LocalDateTime>> parsedData = new HashMap<>();

            lines.skip(1)
                    .map(this::deSerialize)
                    .forEach(entry -> addData(parsedData, entry));

            return parsedData;
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void addData(final HashMap<Crew, List<LocalDateTime>> data, final Entry<Crew, LocalDateTime> entry) {
        final List<LocalDateTime> times = data.getOrDefault(entry.getKey(), new LinkedList<>());
        times.add(entry.getValue());

        data.put(entry.getKey(), times);
    }

    private Entry<Crew, LocalDateTime> deSerialize(final String line) {
        final String[] split = line.split(CREW_DATETIME_DELIMITER);

        final String crewName = split[0];
        final LocalDateTime dateTime = dateTimeFormatter.parse(split[1], LocalDateTime::from);

        return new SimpleImmutableEntry<>(Crew.fromName(crewName), dateTime);
    }
}
