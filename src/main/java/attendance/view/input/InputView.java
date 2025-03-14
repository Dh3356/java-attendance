package attendance.view.input;

import attendance.controller.Command;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface InputView {

    Command inputCommand();

    String inputNickname();

    LocalDateTime inputAttendanceTime();

    String inputUpdateCrewName();

    LocalDate inputUpdateAttendanceDate();

    LocalTime inputUpdateAttendanceTime();
}
