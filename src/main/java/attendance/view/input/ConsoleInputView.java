package attendance.view.input;

import attendance.controller.Command;
import attendance.model.Calendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInputView implements InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public Command inputCommand() {
        System.out.print(getToday());
        System.out.println(" 기능을 선택해주세요.");

        Arrays.stream(Command.values())
                .forEach(command -> System.out.println(command.getMenu()));

        return Command.fromOption(SCANNER.nextLine());
    }

    @Override
    public String inputNickname() {
        System.out.println("닉네임을 입력해주세요.");
        return SCANNER.nextLine();
    }

    @Override
    public LocalDateTime inputAttendanceTime() {
        try {
            System.out.println("등교 시간을 입력해주세요.");
            return LocalDateTime.of(Calendar.TODAY, LocalTime.parse(SCANNER.nextLine()));
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 형식이 아닙니다.");
        }
    }

    @Override
    public String inputUpdateCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해주세요.");
        return SCANNER.nextLine();
    }

    @Override
    public LocalDate inputUpdateAttendanceDate() {
        System.out.println("수정하려는 날짜(일)를 입력해주세요.");
        return LocalDate.of(2024, 12, Integer.parseInt(SCANNER.nextLine()));
    }

    @Override
    public LocalTime inputUpdateAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return LocalTime.parse(SCANNER.nextLine(), dateTimeFormatter);
    }

    private String getToday() {
        final KoreaDayOfWeek dayOfWeek = KoreaDayOfWeek.fromDayOfWeek(Calendar.TODAY.getDayOfWeek());
        return String.format("오늘은 %02d월 %02d일 %s입니다.",
                Calendar.NOW_MONTH,
                Calendar.NOW_DAY,
                dayOfWeek.getName()
        );
    }
}
