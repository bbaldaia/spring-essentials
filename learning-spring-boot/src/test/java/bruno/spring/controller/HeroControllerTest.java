package bruno.spring.controller;

import bruno.spring.commons.FileUtils;
import bruno.spring.commons.HeroUtils;
import bruno.spring.domain.Hero;
import bruno.spring.repository.HeroData;
import bruno.spring.repository.HeroHardCodedRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = HeroController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"bruno.spring"})
@ActiveProfiles("test")
class HeroControllerTest {

    private static final String URI = "/v1/heroes";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private HeroData heroData;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private HeroUtils heroUtils;
    @MockitoSpyBean
    private HeroHardCodedRepository repository;
    private List<Hero> heroList = new ArrayList<>();


    @BeforeEach
    void init() {
        heroList = heroUtils.newHeroList();
    }


    @Test
    @DisplayName("GET v1/heroes findAll returns a list with all heroes when name is null")
    @Order(1)
    void findAll_ReturnsAllHeroes_WhenNameIsNull() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = fileUtils.readResourceFile("hero/get-hero-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URI))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes?name=Hulk findAll returns an one-object list when hero is found")
    @Order(2)
    void findAll_ReturnsHeroFound_WhenNameIsFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = fileUtils.readResourceFile("hero/get-hero-hulk-name-200.json");
        var name = heroList.getFirst().getName();

        mockMvc.perform(MockMvcRequestBuilders.get(URI).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes?=x returns an empty list when hero is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = fileUtils.readResourceFile("hero/get-hero-empty-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URI).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes/1 returns a hero when id is found")
    @Order(4)
    void getById_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = fileUtils.readResourceFile("hero/get-hero-by-id-200.json");
        var id = heroList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes/99 throws ResponseStatusException 404 when hero is not found")
    @Order(5)
    void getById_ThrowsResponseStatusException_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST v1/heroes creates a hero when id is found")
    @Order(6)
    void save_ReturnsHeroCreated_WhenSuccesfull() throws Exception {
        var request = fileUtils.readResourceFile("hero/post-request-hero-200.json");
        var response = fileUtils.readResourceFile("hero/post-response-hero-201.json");
        var heroToSave = Hero.builder().id(99L).name("Thor").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(heroToSave);

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
    @DisplayName("PUT v1/heroes updates hero in the list")
    @Order(7)
    void update_UpdatesHero_WhenFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var request = fileUtils.readResourceFile("hero/put-request-hero-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URI)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/heroes throws ResponseStatusException when hero is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_WhenHeroIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var request = fileUtils.readResourceFile("hero/put-request-hero-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URI)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("delete removes a hero when found")
    @Order(9)
    void delete_RemovesHero_WhenFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var id = heroList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when hero is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenHeroIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var id = 123L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

















