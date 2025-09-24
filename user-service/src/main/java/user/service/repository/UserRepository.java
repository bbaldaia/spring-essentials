package user.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import user.service.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserData userData;

    public List<User> findAll() {
        return userData.getUsers();
    }

    //Optional because the result is either no user found or 1 user found
    public Optional<User> findById(Long id) {
        return userData.getUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    //List because the result can be no user found, 1 user found or N users found
    public List<User> findByName(String firstName) {
        return userData.getUsers()
                .stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                .toList();
    }

    public User create(User user) {
        userData.getUsers().add(user);

        return user;
    }

    public void delete(User user) {
        userData.getUsers().remove(user);
    }

    public void update(User user) {
        delete(user);
        create(user);
    }
}
