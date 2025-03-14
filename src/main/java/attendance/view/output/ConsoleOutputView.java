package attendance.view.output;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.CrewAttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.view.input.KoreaDayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsoleOutputView implements OutputView {


    @Override
    public void printAttendanceLogResponse(final AttendanceLogResponse response) {
        final LocalDate date = response.getDate();
        final LocalTime time = response.getTime().orElse(null);

        final String message = String.format("%s (%s)",
                formatDateTimeWithDayOfWeek(date, time),
                response.getAttendanceStatus());
        System.out.println(message);
    }

    @Override
    public void printUpdateAttendanceResponse(final UpdateAttendanceResponse updateAttendanceResponse) {
        final LocalDate date = updateAttendanceResponse.getPreviousDateTime().toLocalDate();
        final LocalTime previousTime = updateAttendanceResponse.getPreviousDateTime().toLocalTime();
        final LocalTime updatedTime = updateAttendanceResponse.getUpdatedDateTime().toLocalTime();

        final String message = String.format("%s (%s) -> %s (%s) 수정 완료!",
                formatDateTimeWithDayOfWeek(date, previousTime),
                updateAttendanceResponse.getPreviousStatus(),
                formatTime(updatedTime),
                updateAttendanceResponse.getUpdatedStatus());
        System.out.println(message);
    }

    @Override
    public void printCrewAttendanceLogResponse(final CrewAttendanceLogResponse crewAttendanceLogResponse) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", crewAttendanceLogResponse.getCrewName());

        crewAttendanceLogResponse.getAttendanceLogResponses()
                .forEach(this::printAttendanceLogResponse);

        System.out.println();
        printAttendanceStatusStatistics(crewAttendanceLogResponse);
        System.out.printf("%n%s 대상자입니다.%n", crewAttendanceLogResponse.getManagementStatus());
    }

    @Override
    public void printRequiresManagementCrewResponse(
            final List<RequiresManagementCrewResponse> requiresManagementCrewResponses
    ) {

        System.out.println("제적 위험자 조회 결과");
        requiresManagementCrewResponses.forEach(response -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    response.getCrewName(),
                    response.getAbsenceCount(),
                    response.getLateCount(),
                    response.getManagementStatus());
        });
    }

    private void printAttendanceStatusStatistics(final CrewAttendanceLogResponse crewAttendanceLogResponse) {
        crewAttendanceLogResponse.getAttendanceStatusStatistics()
                .forEach((status, count) -> System.out.printf("%s: %d회%n", status, count));
    }

    private String formatDateTimeWithDayOfWeek(final LocalDate date, final LocalTime time) {
        return String.format("%s %s", formatDateWithDayOfWeek(date), formatTime(time));
    }

    private String formatTime(final LocalTime time) {
        if (time == null) {
            return "--:--";
        }
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    private String formatDateWithDayOfWeek(final LocalDate date) {
        return String.format("%02d월 %02d일 %s", date.getMonthValue(), date.getDayOfMonth(),
                KoreaDayOfWeek.fromDayOfWeek(date.getDayOfWeek()).getName());
    }
}
