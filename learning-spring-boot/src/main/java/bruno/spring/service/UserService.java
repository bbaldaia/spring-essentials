package bruno.spring.service;

import bruno.spring.domain.User;
import bruno.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
//any validation is necessary in these methods?
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
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





















