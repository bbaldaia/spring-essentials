package bruno.spring.controller;

import bruno.spring.domain.Hero;
import bruno.spring.repository.HeroData;
import bruno.spring.repository.HeroHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = HeroController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "bruno.spring")
class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResourceLoader resourceLoader;
    @MockitoSpyBean
    private HeroHardCodedRepository repository;
    private List<Hero> heroList = new ArrayList<>();
    @MockitoBean
    private HeroData heroData;

    @BeforeEach
    void init() {
        var dateTime = "2025-09-18T20:39:08.2228625";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        heroList.add(Hero.builder().id(1L).name("Hulk").createdAt(localDateTime).build());
        heroList.add(Hero.builder().id(2L).name("Thanos").createdAt(localDateTime).build());
    }


    @Test
    @DisplayName("GET v1/heroes findAll returns a list with all heroes when name is null")
    @Order(1)
    void findAll_ReturnsAllHeroes_WhenNameIsNull() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = readResourceFile("hero/get-hero-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/heroes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes?name=Hulk findAll returns an one-object list when hero is found")
    @Order(2)
    void findAll_ReturnsHeroFound_WhenNameIsFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = readResourceFile("hero/get-hero-hulk-name-200.json");
        var name = heroList.getFirst().getName();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/heroes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes?=x returns an empty list when hero is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = readResourceFile("hero/get-hero-empty-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/heroes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/heroes/1 returns a hero when id is found")
    @Order(4)
    void getById_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);
        var response = readResourceFile("hero/get-hero-by-id-200.json");
        var id = heroList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/heroes/{id}", id))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/heroes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST v1/heroes creates a hero when id is found")
    @Order(6)
    void save_ReturnsHeroCreated_WhenSuccesfull() throws Exception {
        var request = readResourceFile("hero/post-request-hero-200.json");
        var response = readResourceFile("hero/post-response-hero-201.json");
        var heroToSave = Hero.builder().id(99L).name("Thor").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(heroToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/heroes")
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

        var request = readResourceFile("hero/put-request-hero-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/heroes")
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

        var request = readResourceFile("hero/put-request-hero-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/heroes")
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

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/heroes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when hero is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenHeroIsNotFound() throws Exception {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var id = 123L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/heroes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}

















