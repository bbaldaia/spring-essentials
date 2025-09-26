package user.service.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import user.service.commons.FileUtils;
import user.service.commons.UserUtils;
import user.service.domain.User;
import user.service.repository.UserData;
import user.service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = UserController.class)
@ComponentScan(basePackages = {"user.service"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class UserControllerTest {

    private static final String URI = "/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserData userData;
    private List<User> userList = new ArrayList<>();
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private FileUtils fileUtils;
    @MockitoSpyBean
    private UserRepository repository;


    @BeforeEach
    void init() {
        userList = userUtils.userList();
    }


    @Test
    @Order(1)
    @DisplayName("GET v1/users findAll returns all users in the list")
    void findAll_ReturnsAllUsers_WhenFirstNameIsNull() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URI))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(2)
    @DisplayName("GET v1/users?firstName=Bruno findAll returns user found when first name is not null")
    void findAll_ReturnsUserList_WhenFirstNameIsFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String response = fileUtils.readResourceFile("user/get-user-by-first-name-200.json");
        var firstName = userList.getFirst().getFirstName();

        mockMvc.perform(MockMvcRequestBuilders.get(URI).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(3)
    @DisplayName("GET v1/users?firstName=x findAll returns an empty list when first name is not found")
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String response = fileUtils.readResourceFile("user/get-user-empty-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URI).param("firstName", "x"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(4)
    @DisplayName("GET v1/users/1 findAll returns one user by id when found")
    void findById_ReturnsSingleUser_WhenFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = userList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(5)
    @DisplayName("GET v1/users/99 findAll throws ResponseStatusException (status 404) when id is not found")
    void findById_ThrowsResponseStatusException_WhenIsNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", 99L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(6)
    @DisplayName("POST v1/users creates new user")
    void create_SavesNewUser_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("user/post-request-user-200.json");
        String response = fileUtils.readResourceFile("user/post-response-user-201.json");
        var userToCreate = User.builder().id(3L).firstName("Marcela").lastName("Freire").email("marcela@gmail.com").build();

        BDDMockito.when(repository.create(ArgumentMatchers.any())).thenReturn(userToCreate);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @Order(7)
    @DisplayName("DELETE v1/users/1 removes user from the list when id is found")
    void delete_RemovesUser_WhenFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var id = userList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE v1/users/99 throws ResponseStatusException (status 404) when user is not found")
    void delete_ThrowsResponseStatusException_WhenNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", 99L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(9)
    @DisplayName("UPDATE v1/users changes user data when found")
    void update_UpdatesUser_WhenUserIsFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String request = fileUtils.readResourceFile("user/put-request-user-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("UPDATE v1/users throws ResponseStatusException (status 404) when user is not found")
    void update_ThrowsResponseStatusException_WhenNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        String request = fileUtils.readResourceFile("user/put-request-user-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


































