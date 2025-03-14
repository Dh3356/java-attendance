package attendance.model.domain.crew.vo;

import java.util.Objects;

public class CrewName {

    private final String value;

    private CrewName(final String value) {
        validate(value);
        this.value = value;
    }

    public static CrewName from(final String value) {
        return new CrewName(value);
    }

    private void validate(final String value) {
        if (value.length() < 2 || value.length() > 4) {
            throw new IllegalArgumentException("크루 이름은 2 ~ 4글자 사이여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewName crewName = (CrewName) o;
        return Objects.equals(value, crewName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "CrewName{" +
                "value='" + value + '\'' +
                '}';
    }
}
