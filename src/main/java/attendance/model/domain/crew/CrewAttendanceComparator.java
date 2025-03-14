package attendance.model.domain.crew;

import attendance.model.domain.attendance.CrewAttendance;
import java.util.Comparator;

@FunctionalInterface
public interface CrewAttendanceComparator extends Comparator<CrewAttendance> {
}
