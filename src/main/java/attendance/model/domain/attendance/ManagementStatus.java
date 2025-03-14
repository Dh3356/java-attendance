package attendance.model.domain.attendance;

import attendance.model.domain.attendance.vo.WarningCount;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public enum ManagementStatus {

    EXPULSION("제적", count -> count > 5),
    COUNSELING("면담", count -> count > 2),
    WARNING("경고", count -> count > 1),
    NONE("일반", count -> count <= 1);

    private final String name;
    private final Predicate<Integer> judgement;

    ManagementStatus(String name, Predicate<Integer> judgement) {
        this.name = name;
        this.judgement = judgement;
    }

    public static ManagementStatus fromWarningCount(final WarningCount warningCount) {
        final int policyAppliedAbsenceCount = warningCount.getPolicyAppliedAbsenceCount();

        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(ManagementStatus::ordinal))
                .filter(status -> status.judgement.test(policyAppliedAbsenceCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당되는 관리 상태가 없습니다."));
    }

    public boolean requiresManagement() {
        return this != NONE;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ManagementStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
