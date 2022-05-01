package sat.recruitment.api;

import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Datos {

    public static UserDTO createUser1() {
        return new UserDTO(1L, "Sergio", "sergio.crocetta@gmail.com", "Avenida Rivadavia 77",
                "15151515111", "Normal", Double.valueOf(1000));
    }

    public static UserDTO createUser2() {
        return new UserDTO(2L, "Carlos", "carlos.lopez@gmail.com", "Avenida Belgrano 11",
                "15151515222", "Premium", Double.valueOf(2000));
    }

    public final static List<User> USERS = Arrays.asList(
            new User(5L, "Sergio", "sergio.crocetta@gmail.com", "Avenida Rivadavia 55",
                    "1515151555", "Normal", Double.valueOf(1000)),
            new User(6L, "Matias", "matias.gonzalez@gmail.com", "Pringles 66",
                    "15151515666", "SuperUser", Double.valueOf(2000)),
            new User(7L, "Romina", "romina.garce@gmail.com", "Colombres 77",
                    "15151515777", "Premium", Double.valueOf(3000)));

    public static Optional<User> getUser1() {
        return Optional.of(new User(7L, "Romina", "romina.garce@gmail.com", "Colombres 77",
                "15151515777", "Premium", Double.valueOf(3000)));
    }

}
