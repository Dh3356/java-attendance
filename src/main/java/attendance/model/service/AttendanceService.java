package attendance.model.service;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.CrewAttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.model.Calendar;
import attendance.model.domain.attendance.AttendanceStatus;
import attendance.model.domain.attendance.CrewAttendance;
import attendance.model.domain.crew.Crew;
import attendance.model.domain.crew.CrewAttendanceComparator;
import attendance.model.repository.AttendanceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(final AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceLogResponse attendance(final Crew crew, final LocalDateTime attendanceTime) {
        attendanceRepository.save(crew, attendanceTime);
        return AttendanceLogResponse.fromDateTime(attendanceTime);
    }

    public Crew findCrewByName(final String crewName) {
        return attendanceRepository.findCrewByName(crewName)
                .orElseThrow(() -> new IllegalArgumentException("해당 크루는 존재하지 않습니다."));
    }

    public UpdateAttendanceResponse updateAttendance(final Crew crew, final LocalDateTime updatedTime) {
        final LocalDateTime previousTime = attendanceRepository.findDateTimeByCrewAndDate(
                        crew, updatedTime.toLocalDate()
                )
                .orElseThrow(() -> new IllegalArgumentException("해당 크루는 해당 일자의 출석 기록이 없습니다."));

        attendanceRepository.update(crew, previousTime, updatedTime);

        return UpdateAttendanceResponse.of(
                previousTime,
                updatedTime,
                AttendanceStatus.fromDateTime(previousTime),
                AttendanceStatus.fromDateTime(updatedTime)
        );
    }

    public CrewAttendanceLogResponse getAttendanceLog(final Crew crew) {
        final List<LocalDateTime> attendanceLogs = attendanceRepository.findByCrew(crew);

        final List<AttendanceLogResponse> attendanceLogResponses = mergeAndSotTimeLogResponses(attendanceLogs);
        final CrewAttendance crewAttendance = CrewAttendance.of(crew, attendanceLogs);
        final Map<AttendanceStatus, Integer> attendanceStatusStatistics =
                calculateAttendanceStatusStatistics(attendanceLogResponses);

        return CrewAttendanceLogResponse.of(
                crew,
                attendanceLogResponses,
                crewAttendance,
                attendanceStatusStatistics
        );
    }

    public List<RequiresManagementCrewResponse> getRequiresManagementCrews(
            final CrewAttendanceComparator crewAttendanceComparator
    ) {

        return getSortedCrewAttendance(crewAttendanceComparator).stream()
                .filter(CrewAttendance::requiresManagement)
                .map(RequiresManagementCrewResponse::from)
                .toList();
    }

    private List<AttendanceLogResponse> mergeAndSotTimeLogResponses(final List<LocalDateTime> attendanceLogs) {
        return Stream.concat(
                        makeExistsTimeLogResponses(attendanceLogs).stream(),
                        makeNoneExistsTimeLogResponses(attendanceLogs).stream()
                )
                .sorted(Comparator.comparing(AttendanceLogResponse::getDate))
                .toList();
    }

    private List<AttendanceLogResponse> makeExistsTimeLogResponses(final List<LocalDateTime> attendanceLogs) {
        return attendanceLogs.stream()
                .map(AttendanceLogResponse::fromDateTime)
                .toList();
    }

    private List<AttendanceLogResponse> makeNoneExistsTimeLogResponses(final List<LocalDateTime> attendanceLogs) {
        final List<LocalDate> dateLogs = attendanceLogs.stream()
                .map(LocalDateTime::toLocalDate)
                .toList();

        return Calendar.getNotExistsDatesBeforeToday(dateLogs).stream()
                .map(AttendanceLogResponse::fromAbsenceDate)
                .toList();
    }

    private List<CrewAttendance> getSortedCrewAttendance(final CrewAttendanceComparator crewAttendanceComparator) {
        return attendanceRepository.findAllCrews().stream()
                .map(crew -> CrewAttendance.of(crew, attendanceRepository.findByCrew(crew)))
                .sorted(crewAttendanceComparator)
                .toList();
    }

    private Map<AttendanceStatus, Integer> calculateAttendanceStatusStatistics(
            final List<AttendanceLogResponse> attendanceLogResponses
    ) {

        final List<AttendanceStatus> attendanceStatuses = attendanceLogResponses.stream()
                .map(AttendanceLogResponse::getAttendanceStatus)
                .map(AttendanceStatus::fromName)
                .toList();

        return AttendanceStatus.calculateStatistics(attendanceStatuses);
    }
}
