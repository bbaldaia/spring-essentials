package bruno.spring.repository;

import bruno.spring.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    private final List<User> users = new ArrayList<>();

    {
        users.add(User.builder()
                .id(1L)
                .firstName("Bruno")
                .lastName("Baldaia")
                .email("bfreire@gmail.com")
                .build());

        users.add(User.builder()
                .id(2L)
                .firstName("Gustavo")
                .lastName("Freire")
                .email("gustavo@gmail.com")
                .build());
    }

    public List<User> getUsers() {
        return users;
    }
}
