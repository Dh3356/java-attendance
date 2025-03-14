package attendance.model.crew;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {

    @DisplayName("크루 생성 시 닉네임이 2 ~ 5글자가 아니면 IllegalArgumentException 이 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "", "abcdef"})
    void validateNickName(final String nickName) {

        // When & Then
        assertThatIllegalArgumentException().isThrownBy(() -> new Crew(nickName));
    }
}
