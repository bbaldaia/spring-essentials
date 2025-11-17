package bruno.spring.service;

import bruno.spring.domain.User;
import bruno.spring.exception.InvalidEmailException;
import bruno.spring.exception.NotFoundException;
import bruno.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(firstName);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("USER ID NOT FOUND!"));
    }

    public User create(User user) {
        assertEmailExists(user.getEmail());
        return repository.save(user);
    }

    public void delete(Long id) {
        User userToDelete = findById(id);

        repository.delete(userToDelete);
    }

    public void update(User user) {
        findById(user.getId());
        assertEmailExists(user.getEmail(), user.getId());
        repository.save(user);
    }

    public void assertEmailExists(String email) {
        repository.findByEmail(email).ifPresent(user -> throwEmailExistsException());
    }

    public void assertEmailExists(String email, Long id) {
        repository.findByEmailAndIdNot(email, id).ifPresent(user -> throwEmailExistsException());
    }

    private void throwEmailExistsException() {
        throw new InvalidEmailException("E-mail already exists");
    }
}