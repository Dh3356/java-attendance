package attendance.model.domain.attendance.vo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class WarningCount {

    private final AbsenceCount absenceCount;
    private final LateCount lateCount;

    private WarningCount(final AbsenceCount absenceCount, final LateCount lateCount) {
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
    }

    public static WarningCount fromDateTimes(final List<LocalDateTime> dateTimes) {
        final AbsenceCount absenceCount = AbsenceCount.fromDateTimes(dateTimes);
        final LateCount lateCount = LateCount.fromDateTimes(dateTimes);

        return new WarningCount(absenceCount, lateCount);
    }

    public int getPolicyAppliedAbsenceCount() {
        return absenceCount.getPolicyAppliedValue(lateCount);
    }

    public int getPolicyAppliedLateCount() {
        return lateCount.calculatePolicyAppliedValue();
    }

    public int getAbsenceCount() {
        return absenceCount.getValue();
    }

    public int getLateCount() {
        return lateCount.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarningCount that = (WarningCount) o;
        return Objects.equals(absenceCount, that.absenceCount) && Objects.equals(lateCount,
                that.lateCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absenceCount, lateCount);
    }

    @Override
    public String toString() {
        return "WarningCount{" +
                "absenceCount=" + absenceCount +
                ", lateCount=" + lateCount +
                '}';
    }
}
