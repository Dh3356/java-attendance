package attendance.dto;

import attendance.model.domain.attendance.CrewAttendance;

public class RequiresManagementCrewResponse {

    private final String crewName;
    private final int absenceCount;
    private final int lateCount;
    private final String managementStatus;

    private RequiresManagementCrewResponse(
            final String crewName,
            final int absenceCount,
            final int lateCount,
            final String managementStatus
    ) {

        this.crewName = crewName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.managementStatus = managementStatus;
    }

    public static RequiresManagementCrewResponse from(final CrewAttendance crewAttendance) {
        return new RequiresManagementCrewResponse(
                crewAttendance.getCrewName(),
                crewAttendance.getAbsenceCount(),
                crewAttendance.getLateCount(),
                crewAttendance.getManagementStatusName()
        );
    }

    public String getCrewName() {
        return crewName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public String getManagementStatus() {
        return managementStatus;
    }

    @Override
    public String toString() {
        return "DangerCrewResponse{" +
                "crewName='" + crewName + '\'' +
                ", absenceCount=" + absenceCount +
                ", lateCount=" + lateCount +
                ", managementStatus='" + managementStatus + '\'' +
                '}' + '\n';
    }
}
