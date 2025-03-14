package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @Test
    void isChirstmas() {

        // Given
        final LocalDate christmas = LocalDate.of(2024, 12, 25);

        // When
        final boolean actual = Holiday.isHoliday(christmas);

        // Then
        assertThat(actual).isTrue();
    }
}
