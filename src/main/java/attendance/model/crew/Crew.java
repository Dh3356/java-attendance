package attendance.model.crew;

import java.util.Objects;

public class Crew {

    private final String nickName;

    public Crew(final String nickName) {
        validateNickName(nickName);
        this.nickName = nickName;
    }

    private void validateNickName(final String nickName) {
        if (nickName.length() < 2 || nickName.length() > 5) {
            throw new IllegalArgumentException("크루의 닉네임은 2 ~ 5글자 사이여야 합니다.");
        }
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickName, crew.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickName);
    }

    @Override
    public String toString() {
        return "Crew{" +
                "nickName='" + nickName + '\'' +
                '}';
    }
}
