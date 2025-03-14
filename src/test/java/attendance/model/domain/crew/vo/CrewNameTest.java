package attendance.model.domain.crew.vo;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewNameTest {

    @DisplayName("크루 이름이 2 ~ 4글자 사이가 아니면 IllegalArgumentException을 던진다.")
    @ParameterizedTest(name = "crewName: {0}")
    @ValueSource(strings = {"a", "", "abcde"})
    void validate(final String crewName) {
        // When & Then
        assertThatIllegalArgumentException().isThrownBy(() -> CrewName.from(crewName));
    }
}
