package attendance.model.domain.crew;

import attendance.model.domain.crew.vo.CrewName;
import java.util.Objects;

public class Crew {

    private final CrewName name;

    private Crew(final CrewName name) {
        this.name = name;
    }

    public static Crew fromName(final String name) {
        return new Crew(CrewName.from(name));
    }

    public String getName() {
        return name.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Crew{" +
                "name='" + name + '\'' +
                '}';
    }
}
