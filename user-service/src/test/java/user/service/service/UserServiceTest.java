package user.service.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import user.service.commons.UserUtils;
import user.service.domain.User;
import user.service.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @InjectMocks
    private UserUtils userUtils;
    @Mock
    private UserRepository repository;
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    void init() {
        userList = userUtils.userList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list of all users found when first name is null")
    void findAll_ReturnsAllUsers_WhenFirstNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(userList);

        var users = service.findAll(null);

        Assertions.assertThat(users).isNotEmpty().hasSameElementsAs(userList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns user found when first name is not null")
    void findAll_ReturnsUserList_WhenFirstNameIsFound() {
        var user = userList.getFirst();
        var oneUserList = Collections.singletonList(user);

        BDDMockito.when(repository.findByFirstName(user.getFirstName()))
                .thenReturn(oneUserList);

        List<User> userFound = service.findAll(user.getFirstName());

        Assertions.assertThat(userFound).isNotEmpty().isNotNull().containsAll(oneUserList);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when first name is not found")
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound() {
        var nonExistingFirstName = "Antonio";

        List<User> emptyUserList = Collections.emptyList();

        BDDMockito.when(repository.findByFirstName(nonExistingFirstName)).thenReturn(emptyUserList);

        var noValueUserList = service.findAll(nonExistingFirstName);

        Assertions.assertThat(noValueUserList).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns one user by id when found")
    void findById_ReturnsSingleUser_WhenFound() {
        var expectedUser = userList.getFirst();

        BDDMockito.when(repository.findById(expectedUser.getId()))
                .thenReturn(Optional.of(expectedUser));

        var userById = service.findById(expectedUser.getId());

        Assertions.assertThat(userById).isNotNull().isEqualTo(expectedUser);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws ResponseStatusException (status 404) when id is not found")
    void findById_ThrowsResponseStatusException_WhenIsNotFound() {
        var nonExistingId = 99L;

        BDDMockito.when(repository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(nonExistingId))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("create saves new user")
    void create_SavesNewUser_WhenSuccessful() {
        var userToCreate = User.builder()
                .id(50L)
                .firstName("Darlei")
                .lastName("Freire")
                .email("darlei@gmail.com")
                .build();

        BDDMockito.when(repository.create(userToCreate)).thenReturn(userToCreate);

        var createdUser = service.create(userToCreate);

        Assertions.assertThat(createdUser).isEqualTo(userToCreate).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete removes user from the list when id is found")
    void delete_RemovesUser_WhenFound() {
        var expectedUser = userList.getFirst();

        BDDMockito.when(repository.findById(expectedUser.getId()))
                .thenReturn(Optional.of(expectedUser));

        BDDMockito.doNothing().when(repository).delete(expectedUser);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(expectedUser.getId()));
    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException (status 404) when user is not found")
    void delete_ThrowsResponseStatusException_WhenNotFound() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(ArgumentMatchers.anyLong()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update changes user data when found")
    void update_UpdatesUser_WhenUserIsFound() {
        var expectedUser = userList.getFirst();
        expectedUser.setFirstName("Random Name");

        BDDMockito.when(repository.findById(expectedUser.getId()))
                .thenReturn(Optional.of(expectedUser));

        BDDMockito.doNothing().when(repository).update(expectedUser);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(expectedUser));
    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException (status 404) when user is not found")
    void update_ThrowsResponseStatusException_WhenNotFound() {
        var expectedUser = userList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(expectedUser))
                .isInstanceOf(ResponseStatusException.class);
    }
}























