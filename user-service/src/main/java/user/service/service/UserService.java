package user.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import user.service.domain.User;
import user.service.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
//any validation is necessary in these methods?
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByName(firstName);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER ID NOT FOUND!"));
    }

    public User create(User user) {
        return repository.create(user);
    }

    public void delete(Long id) {
        User userToDelete = findById(id);

        repository.delete(userToDelete);
    }

    public void update(User user) {
        findById(user.getId());

        repository.update(user);
    }
}