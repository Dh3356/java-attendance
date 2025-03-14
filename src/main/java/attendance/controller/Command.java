package attendance.controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum Command {

    ATTENDANCE("1", "출석 확인", Controller::attendance),
    UPDATE_ATTENDANCE("2", "출석 수정", Controller::updateAttendance),
    GET_ATTENDANCE_LOG("3", "크루별 출석 기록 확인", Controller::checkCrewAttendance),
    GET_REQUIRES_MANAGEMENT_CREWS("4", "제적 위험자 확인", Controller::printRequiresManagementCrews),
    QUIT("Q", "종료", Controller::quit);

    private final String option;
    private final String description;
    private final Consumer<Controller> action;

    Command(final String option, final String description, final Consumer<Controller> action) {
        this.option = option;
        this.description = description;
        this.action = action;
    }

    public static Command fromOption(final String option) {
        return Arrays.stream(values())
                .filter(command -> command.option.equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 기능은 존재하지 않습니다."));
    }

    public String getMenu() {
        return option + ". " + description;
    }

    public void run(final Controller controller) {
        action.accept(controller);
    }
}
