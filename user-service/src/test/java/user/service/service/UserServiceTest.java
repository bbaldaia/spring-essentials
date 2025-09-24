package user.service.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.service.commons.UserUtils;
import user.service.domain.User;
import user.service.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    UserService service;
    @InjectMocks
    UserUtils userUtils;
    @Mock
    UserRepository repository;
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    void init() {
        userList = userUtils.userList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list of all users found when first name is null")
    void findAll_ReturnsAllUsers_WhenNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(userList);

        List<User> users = service.findAll(null);

        Assertions.assertThat(users).isNotEmpty().hasSameElementsAs(userList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns user found when first name is not null")
    void findAll_ReturnsUserList_WhenNameIsFound() {
        User user = userList.getFirst();
        List<User> expectedUserFound = Collections.singletonList(user);

        BDDMockito.when(repository.findByName(user.getFirstName()))
                .thenReturn(expectedUserFound);

        List<User> userFound = service.findAll(user.getFirstName());

        Assertions.assertThat(userFound).isNotEmpty().isNotNull().containsAll(expectedUserFound);

    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when name is not found")
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() {
        String fakeName = "Antonio";

        List<User> emptyList = Collections.emptyList();

        BDDMockito.when(repository.findByName(fakeName)).thenReturn(emptyList);

        List<User> noValueList = service.findAll(fakeName);

        Assertions.assertThat(noValueList).isNotNull().isEmpty();
    }
}























