package attendance.model.attendance.repository;

import attendance.model.attendance.log.AttendanceLog;
import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.attendance.log.CrewAttendanceLogDeserializer;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CrewAttendanceRepository {

    private final Map<Crew, AttendanceLogs> crewAttendanceLogs;

    public CrewAttendanceRepository(
            final CrewAttendanceLogDeserializer crewAttendanceLogDeserializer,
            final Path path,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        this.crewAttendanceLogs = crewAttendanceLogDeserializer.deserializeFromCsv(path, campusOperationPolicy);
    }

    public void add(final Crew crew, final AttendanceLog attendanceLog) {
        findByCrew(crew).add(attendanceLog);
    }

    private AttendanceLogs findByCrew(final Crew crew) {
        return crewAttendanceLogs.computeIfAbsent(crew, none -> {
            throw new IllegalArgumentException("해당 크루가 존재하지 않습니다.");
        });
    }

    public void update(final Crew crew, final AttendanceLog from, final AttendanceLog to) {
        findByCrew(crew).update(from, to);
    }

    public AttendanceLogs findByCrewBetween(
            final Crew crew,
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy) {

        return findByCrew(crew).getAllAttendanceLogsBetween(from, to, campusOperationPolicy);
    }

    public Map<Crew, AttendanceLogs> getCrewAttendanceLogs() {
        return Collections.unmodifiableMap(crewAttendanceLogs);
    }

    public AttendanceLog findAttendanceLogByDate(final Crew crew, final LocalDate date) {
        return findByCrew(crew).findAttendanceLogByDate(date);
    }

    public Map<Crew, AttendanceLogs> getWarningCrewAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return crewAttendanceLogs.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(),
                        entry.getValue().getAllAttendanceLogsBetween(from, to, campusOperationPolicy)))
                .filter(entry -> entry.getValue().isWarning())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (existing, replacement) -> existing,
                                HashMap::new)
                );
    }
}
