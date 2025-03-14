package attendance.controller.dto;

import attendance.model.attendance.log.AttendanceLogs;
import attendance.model.crew.Crew;

public class WarningCrewResponse {

    private final String crewNickName;
    private final int absenceCount;
    private final int lateCount;
    private final int policyAppliedAbsenceCount;
    private final int policyAppliedLateCount;
    private final String crewStatus;

    public WarningCrewResponse(
            String crewNickName,
            int absenceCount,
            int lateCount,
            int policyAppliedAbsenceCount,
            int policyAppliedLateCount,
            String crewStatus
    ) {

        this.crewNickName = crewNickName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.policyAppliedAbsenceCount = policyAppliedAbsenceCount;
        this.policyAppliedLateCount = policyAppliedLateCount;
        this.crewStatus = crewStatus;
    }

    public static WarningCrewResponse from(final Crew crew, AttendanceLogs attendanceLogs) {
        return new WarningCrewResponse(
                crew.getNickName(),
                attendanceLogs.getAbsenceCount(),
                attendanceLogs.getLateCount(),
                attendanceLogs.getPolicyAppliedAbsenceCount(),
                attendanceLogs.getPolicyAppliedLateCount(),
                attendanceLogs.getCrewStatus().getName()
        );
    }

    public String getCrewNickName() {
        return crewNickName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getPolicyAppliedAbsenceCount() {
        return policyAppliedAbsenceCount;
    }

    public int getPolicyAppliedLateCount() {
        return policyAppliedLateCount;
    }

    public String getCrewStatus() {
        return crewStatus;
    }
}
