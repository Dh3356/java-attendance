package attendance.view.output;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.CrewAttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import java.util.List;

public interface OutputView {

    void printAttendanceLogResponse(AttendanceLogResponse response);

    void printUpdateAttendanceResponse(UpdateAttendanceResponse updateAttendanceResponse);

    void printCrewAttendanceLogResponse(CrewAttendanceLogResponse crewAttendanceLogResponse);

    void printRequiresManagementCrewResponse(
            List<RequiresManagementCrewResponse> requiresManagementCrewResponses);
}
