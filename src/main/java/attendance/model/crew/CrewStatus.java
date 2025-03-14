package attendance.model.crew;

import attendance.model.attendance.log.AttendanceLogs;

public enum CrewStatus {

    WARNING("경고"),
    CONSULTATION("면담"),
    EXPULSION("제적"),
    NORMAL("정상");

    private static final int EXPULSION_THRESHOLD = 5;
    private static final int CONSULTATION_THRESHOLD = 2;
    private static final int WARNING_THRESHOLD = 1;


    private final String name;

    CrewStatus(String name) {
        this.name = name;
    }

    public static CrewStatus fromAttendanceLogs(final AttendanceLogs attendanceLogs) {
        final int absenceCount = attendanceLogs.getPolicyAppliedAbsenceCount();

        if (absenceCount > EXPULSION_THRESHOLD) {
            return EXPULSION;
        }
        if (absenceCount > CONSULTATION_THRESHOLD) {
            return CONSULTATION;
        }
        if (absenceCount > WARNING_THRESHOLD) {
            return WARNING;
        }
        return NORMAL;
    }

    public String getName() {
        return name;
    }
}
