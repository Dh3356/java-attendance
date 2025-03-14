package attendance.controller.dto;

import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.crew.Crew;
import attendance.model.crew.CrewStatus;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CrewAttendanceResponse(
        String crewNickName,
        List<AttendanceResponse> attendanceResponses,
        Map<String, Integer> attendanceStatusStatistics, String crewStatus
) {

    public static CrewAttendanceResponse from(final Crew crew, final AttendanceLogs attendanceLogs,
                                              final Map<AttendanceStatus, Integer> attendanceStatusStatistics) {
        final List<AttendanceResponse> attendanceResponses = attendanceLogs.values().stream()
                .map(AttendanceResponse::fromAttendanceLog)
                .toList();

        final Map<String, Integer> simplifiedStatistics = attendanceStatusStatistics.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().getName(),
                                Map.Entry::getValue
                        )
                );

        return new CrewAttendanceResponse(
                crew.getNickName(),
                attendanceResponses,
                simplifiedStatistics,
                CrewStatus.fromAttendanceLogs(attendanceLogs).getName()
        );
    }
}
