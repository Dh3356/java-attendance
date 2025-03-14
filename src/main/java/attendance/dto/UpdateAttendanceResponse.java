package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDateTime;

public class UpdateAttendanceResponse {

    private final LocalDateTime previousDateTime;
    private final LocalDateTime updatedDateTime;
    private final String previousStatus;
    private final String updatedStatus;

    private UpdateAttendanceResponse(
            final LocalDateTime previousDateTime,
            final LocalDateTime updatedDateTime,
            final String previousStatus,
            final String updatedStatus
    ) {

        this.previousDateTime = previousDateTime;
        this.updatedDateTime = updatedDateTime;
        this.previousStatus = previousStatus;
        this.updatedStatus = updatedStatus;
    }

    public static UpdateAttendanceResponse of(
            final LocalDateTime before,
            final LocalDateTime after,
            final AttendanceStatus beforeStatus,
            final AttendanceStatus afterStatus
    ) {

        return new UpdateAttendanceResponse(
                before,
                after,
                beforeStatus.getName(),
                afterStatus.getName()
        );
    }

    public LocalDateTime getPreviousDateTime() {
        return previousDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getUpdatedStatus() {
        return updatedStatus;
    }
}
