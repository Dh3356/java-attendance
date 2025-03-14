package attendance.controller;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.model.domain.crew.Crew;
import attendance.model.domain.crew.CrewAttendanceComparator;
import attendance.model.service.AttendanceService;
import attendance.view.input.InputView;
import attendance.view.output.OutputView;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultController implements Controller {

    private static final String ERROR_FORMAT = "[ERROR] %s";

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService attendanceService;
    private final CrewAttendanceComparator crewAttendanceComparator;

    public DefaultController(
            final InputView inputView,
            final OutputView outputView,
            final AttendanceService attendanceService,
            final CrewAttendanceComparator crewAttendanceComparator
    ) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceService = attendanceService;
        this.crewAttendanceComparator = crewAttendanceComparator;
    }

    @Override
    public void run() {
        Command command;
        do {
            command = inputCommand();
            command.run(this);
        } while (command != Command.QUIT);
    }

    private Command inputCommand() {
        try {
            return inputView.inputCommand();
        } catch (final IllegalArgumentException e) {
            handleException(e);
            return inputCommand();
        }
    }

    @Override
    public void attendance() {
        try {
            final String crewName = inputView.inputNickname();
            final Crew crew = attendanceService.findCrewByName(crewName);

            final LocalDateTime attendanceTime = inputView.inputAttendanceTime();

            final AttendanceLogResponse response = attendanceService.attendance(crew, attendanceTime);

            outputView.printAttendanceLogResponse(response);
        } catch (final RuntimeException runtimeException) {
            handleException(runtimeException);
        }
    }

    @Override
    public void updateAttendance() {
        try {
            final String crewName = inputView.inputUpdateCrewName();
            final Crew crew = attendanceService.findCrewByName(crewName);

            final LocalDateTime updatedTime = LocalDateTime.of(
                    inputView.inputUpdateAttendanceDate(),
                    inputView.inputUpdateAttendanceTime()
            );

            final UpdateAttendanceResponse updateAttendanceResponse = attendanceService.updateAttendance(crew,
                    updatedTime);

            outputView.printUpdateAttendanceResponse(updateAttendanceResponse);
        } catch (final RuntimeException runtimeException) {
            handleException(runtimeException);
        }
    }

    @Override
    public void checkCrewAttendance() {
        try {
            final String crewName = inputView.inputNickname();
            final Crew crew = attendanceService.findCrewByName(crewName);

            outputView.printCrewAttendanceLogResponse(attendanceService.getAttendanceLog(crew));
        } catch (final RuntimeException runtimeException) {
            handleException(runtimeException);
        }
    }

    @Override
    public void printRequiresManagementCrews() {
        try {
            final List<RequiresManagementCrewResponse> responses =
                    attendanceService.getRequiresManagementCrews(crewAttendanceComparator);

            outputView.printRequiresManagementCrewResponse(responses);
        } catch (final RuntimeException runtimeException) {
            handleException(runtimeException);
        }
    }

    @Override
    public void quit() {
    }

    private void handleException(final RuntimeException runtimeException) {
        System.out.printf((ERROR_FORMAT) + "%n", runtimeException.getMessage());
    }
}
