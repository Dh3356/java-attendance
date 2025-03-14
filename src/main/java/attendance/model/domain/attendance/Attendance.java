package attendance.model.domain.attendance;

import attendance.model.domain.attendance.vo.WarningCount;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Attendance {

    private final WarningCount warningCount;
    private final ManagementStatus managementStatus;

    public Attendance(final WarningCount warningCount, final ManagementStatus managementStatus) {
        this.warningCount = warningCount;
        this.managementStatus = managementStatus;
    }

    public static Attendance fromDateTimes(final List<LocalDateTime> dateTimes) {
        final WarningCount warningCount = WarningCount.fromDateTimes(dateTimes);
        return new Attendance(warningCount, ManagementStatus.fromWarningCount(warningCount));
    }

    public boolean requiresManagement() {
        return managementStatus.requiresManagement();
    }

    public int getPolicyAppliedAbsenceCount() {
        return warningCount.getPolicyAppliedAbsenceCount();
    }

    public int getPolicyAppliedLateCount() {
        return warningCount.getPolicyAppliedLateCount();
    }

    public int getAbsenceCount() {
        return warningCount.getAbsenceCount();
    }

    public int getLateCount() {
        return warningCount.getLateCount();
    }

    public String getManagementStatusName() {
        return managementStatus.getName();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(warningCount, that.warningCount) && managementStatus == that.managementStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(warningCount, managementStatus);
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "warningCount=" + warningCount +
                ", managementStatus=" + managementStatus +
                '}';
    }
}
