package attendance.model.repository;

import attendance.model.domain.crew.Crew;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceRepository {

    private final Map<Crew, List<LocalDateTime>> values;

    public AttendanceRepository(final CrewAttendanceDeserializer crewAttendanceDeserializer, final Path path) {
        this.values = crewAttendanceDeserializer.readAll(path);
    }

    public List<LocalDateTime> findByCrew(final Crew crew) {
        validateCrewExistence(crew);
        return values.get(crew).stream()
                .toList();
    }

    public void save(final Crew crew, final LocalDateTime dateTime) {
        if (existsByCrew(crew)) {
            validateConflict(crew, dateTime);
        }
        final List<LocalDateTime> times = values.getOrDefault(crew, new ArrayList<>());
        times.add(dateTime);
        values.put(crew, times);
    }

    public boolean existsByCrew(final Crew crew) {
        return values.containsKey(crew);
    }

    public void update(final Crew crew, final LocalDateTime previousTime, final LocalDateTime updatedTime) {
        validateCrewExistence(crew);
        validateDateTimeExistenceByCrew(crew, previousTime);

        values.get(crew).add(updatedTime);
        deleteAttendanceByCrew(crew, previousTime);
    }

    public void deleteAttendanceByCrew(final Crew crew, final LocalDateTime dateTimeToDelete) {
        validateCrewExistence(crew);
        validateDateTimeExistenceByCrew(crew, dateTimeToDelete);

        values.get(crew).remove(dateTimeToDelete);
    }

    public Optional<LocalDateTime> findDateTimeByCrewAndDate(final Crew crew, final LocalDate date) {
        validateCrewExistence(crew);

        return values.get(crew).stream()
                .filter(time -> time.toLocalDate().equals(date))
                .findFirst();
    }

    public List<Crew> findAllCrews() {
        return values.keySet().stream()
                .toList();
    }

    public Optional<Crew> findCrewByName(final String crewName) {
        return values.keySet().stream()
                .filter(crew -> crew.getName().equals(crewName))
                .findFirst();
    }

    private void validateCrewExistence(final Crew crew) {
        if (!existsByCrew(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    private void validateDateTimeExistenceByCrew(final Crew crew, final LocalDateTime dateTime) {
        if (values.get(crew).stream().noneMatch(value -> value.isEqual(dateTime))) {
            throw new IllegalArgumentException("해당 크루는 해당 일시의 출석 기록이 없습니다.");
        }
    }

    private void validateConflict(final Crew crew, final LocalDateTime dateTime) {
        if (values.get(crew).stream()
                .anyMatch(value -> value.toLocalDate().equals(dateTime.toLocalDate()))
        ) {
            throw new IllegalStateException("금일 출석 기록이 이미 존재합니다.");
        }
    }
}
