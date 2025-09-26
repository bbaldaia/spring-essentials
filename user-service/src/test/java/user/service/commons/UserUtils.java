package user.service.commons;

import org.springframework.stereotype.Component;
import user.service.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> userList() {
        var bruno = User.builder().id(1L).firstName("Bruno").lastName("Baldaia").email("bruno@gmail.com").build();
        var gustavo = User.builder().id(2L).firstName("Gustavo").lastName("Freire").email("gustavo@gmail.com").build();

        return new ArrayList<>(List.of(bruno, gustavo));
    }
}