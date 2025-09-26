package user.service.repository;

//import org.junit.jupiter.api.Assertions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.service.commons.UserUtils;
import user.service.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepository repository;
    @InjectMocks
    private UserUtils userUtils;
    @Mock
    private UserData userData;
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    void init() {
        userList = userUtils.userList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list of all users found")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var users = repository.findAll();

        Assertions.assertThat(users).isNotNull().hasSameElementsAs(userList);
    }

    @Test
    @Order(2)
    @DisplayName("findById returns one user by id when found")
    void findById_ReturnsSingleUser_WhenFound() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();

        var optionalUser = repository.findById(expectedUser.getId());

        Assertions.assertThat(optionalUser).isPresent().contains(expectedUser);
    }

    @Test
    @Order(3)
    @DisplayName("findById returns empty value when id is not found")
    void findById_ReturnsEmpty_WhenNotFound() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var randomId = 99L;

        var optionalUser = repository.findById(randomId);

        Assertions.assertThat(optionalUser).isEmpty().isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName("findByFirstName returns user found when first name is not null")
    void findByFirstName_ReturnsUserList_WhenNameIsFound() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var expectedUser = userList.getFirst();

        List<User> userByFirstName = repository.findByFirstName(expectedUser.getFirstName());

        Assertions.assertThat(userByFirstName).isNotEmpty().isNotNull().contains(expectedUser);
    }

    @Test
    @Order(5)
    @DisplayName("findByFirstName returns an empty list when first name is null")
    void findByFirstName_ReturnsEmptyList_WhenNameIsNotFound() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        List<User> userByFirstName = repository.findByFirstName(null);

        Assertions.assertThat(userByFirstName).isNotNull().isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("create saves a new user in list when successful")
    void create_SavesNewUser_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var newUser = User.builder()
                .id(3L)
                .firstName("Marcela")
                .lastName("Freire")
                .email("marcela@gmail.com")
                .build();

        var createdUser = repository.create(newUser);

        Assertions.assertThat(createdUser).isEqualTo(newUser).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete removes user from list")
    void delete_RemovesUser_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var userToDelete = userList.getFirst();

        repository.delete(userToDelete);

        Assertions.assertThat(userList).isNotEmpty().doesNotContain(userToDelete);
    }

    @Test
    @Order(8)
    @DisplayName("update changes user data when successful")
    void update_UpdatesUser_WhenSuccessful() {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Darlei");
        userToUpdate.setLastName("Freire");

        repository.update(userToUpdate);

        Assertions.assertThat(userList).contains(userToUpdate);

        var optionalUpdatedUser = repository.findById(userToUpdate.getId());

        Assertions.assertThat(optionalUpdatedUser).isPresent();
        Assertions.assertThat(optionalUpdatedUser.get().getFirstName()).isEqualTo(userToUpdate.getFirstName());
    }
}





























