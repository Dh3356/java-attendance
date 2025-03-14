package attendance.model.attendance.log;

import attendance.model.attendance.status.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.CrewStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record AttendanceLogs(
        List<AttendanceLog> values
) {

    private static final int LATE_TO_ABSENCE_RATIO = 3;

    public AttendanceLogs(final List<AttendanceLog> values) {
        this.values = new ArrayList<>(values);
    }

    public static AttendanceLogs fromLocalDateTimes(
            final List<LocalDateTime> dateTimes,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        final List<AttendanceLog> attendanceLogs = dateTimes.stream()
                .map(dateTime -> AttendanceLog.fromDateTime(dateTime, campusOperationPolicy))
                .toList();

        return new AttendanceLogs(attendanceLogs);
    }

    public AttendanceLogs getAllAttendanceLogsBetween(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return new AttendanceLogs(Stream.concat(
                        values.stream(),
                        getAbsenceAttendanceLogs(from, to, campusOperationPolicy).stream()
                )
                .sorted(Comparator.comparing(AttendanceLog::getDate))
                .toList());
    }

    private List<AttendanceLog> getAbsenceAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return from.datesUntil(to)
                .filter(campusOperationPolicy::isOpenDate)
                .filter(date -> !containsDate(date))
                .map(date -> AttendanceLog.fromAbsenceDate(date, campusOperationPolicy))
                .toList();
    }

    private boolean containsDate(final LocalDate date) {
        return values.stream().anyMatch(attendanceLog -> attendanceLog.isSameDate(date));
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusStatistics() {
        return AttendanceStatus.getStatistics(getAllAttendanceStatuses());
    }

    public List<AttendanceStatus> getAllAttendanceStatuses() {
        return values.stream()
                .map(AttendanceLog::getAttendanceStatus)
                .toList();
    }

    public void add(final AttendanceLog attendanceLog) {
        values.add(attendanceLog);
    }

    public int getPolicyAppliedAbsenceCount() {
        return getAbsenceCount() + getLateCount() / LATE_TO_ABSENCE_RATIO;
    }

    public int getAbsenceCount() {
        return Math.toIntExact(
                values.stream()
                        .filter(AttendanceLog::isAbsence)
                        .count()
        );
    }

    public int getPolicyAppliedLateCount() {
        return getLateCount() % LATE_TO_ABSENCE_RATIO;
    }

    public int getLateCount() {
        return Math.toIntExact(
                values.stream()
                        .filter(AttendanceLog::isLate)
                        .count()
        );
    }

    public void update(final AttendanceLog from, final AttendanceLog to) {
        final AttendanceLog targetAttendanceLog = values.stream()
                .filter(log -> log.isSameDate(from.getDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석 기록이 존재하지 않습니다."));

        values.remove(targetAttendanceLog);
        values.add(to);
    }

    public boolean contains(final AttendanceLog attendanceLog) {
        return values.contains(attendanceLog);
    }

    public AttendanceLog findAttendanceLogByDate(final LocalDate date) {
        return values.stream()
                .filter(attendanceLog -> attendanceLog.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public CrewStatus getCrewStatus() {
        return CrewStatus.fromAttendanceLogs(this);
    }

    public boolean isWarning() {
        CrewStatus status = CrewStatus.fromAttendanceLogs(this);

        return status == CrewStatus.WARNING || status == CrewStatus.CONSULTATION || status == CrewStatus.EXPULSION;
    }
}
