package application;

import java.util.Objects;

public class Equals {

    String name;
    String email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equals equals = (Equals) o;
        return Objects.equals(name, equals.name) && Objects.equals(email, equals.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
