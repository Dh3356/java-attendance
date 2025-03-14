package attendance.model.domain.attendance;

import attendance.model.domain.crew.Crew;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CrewAttendance {

    private final Crew crew;
    private final Attendance attendance;

    private CrewAttendance(final Crew crew, final Attendance attendance) {
        this.crew = crew;
        this.attendance = attendance;
    }

    public static CrewAttendance of(final Crew crew, final List<LocalDateTime> dateTimes) {
        return new CrewAttendance(crew, Attendance.fromDateTimes(dateTimes));
    }

    public boolean requiresManagement() {
        return attendance.requiresManagement();
    }

    public int getPolicyAppliedAbsenceCount() {
        return attendance.getPolicyAppliedAbsenceCount();
    }

    public int getPolicyAppliedLateCount() {
        return attendance.getPolicyAppliedLateCount();
    }

    public String getCrewName() {
        return crew.getName();
    }

    public int getAbsenceCount() {
        return attendance.getAbsenceCount();
    }

    public int getLateCount() {
        return attendance.getLateCount();
    }

    public String getManagementStatusName() {
        return attendance.getManagementStatusName();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewAttendance that = (CrewAttendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(attendance, that.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, attendance);
    }

    @Override
    public String toString() {
        return "CrewAttendance{" +
                "crew=" + crew +
                ", attendance=" + attendance +
                '}';
    }
}
