package hero.service.service;

import hero.service.commons.HeroUtils;
import hero.service.domain.Hero;
import hero.service.repository.HeroHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HeroServiceTest {

    @InjectMocks
    private HeroService service;
    @InjectMocks
    private HeroUtils heroUtils;
    @Mock
    private HeroHardCodedRepository repository;
    private List<Hero> heroList = new ArrayList<>();

    @BeforeEach
    void init() {
        heroList = heroUtils.newHeroList();
    }

    @Test
    @DisplayName("findAll returns a list with all heroes when name is null")
    @Order(1)
    void findAll_ReturnsAllHeroes_WhenNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(heroList);

        var heroes = service.findAll(null);
        Assertions.assertThat(heroes).isNotNull().hasSameElementsAs(heroList);
    }

    @Test
    @DisplayName("findAll returns an one-object list when hero is found")
    @Order(2)
    void findAll_ReturnsHeroFound_WhenNameIsFound() {
        var hero = heroList.getFirst();
        var expectedHeroFound = Collections.singletonList(hero);

        BDDMockito.when(repository.findByName(hero.getName())).thenReturn(expectedHeroFound);

        var heroFound = service.findAll(hero.getName());

        Assertions.assertThat(heroFound).containsAll(expectedHeroFound);
    }

    @Test
    @DisplayName("findAll returns an empty list when hero is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() {
        var randomHero = "Random Hero";

        BDDMockito.when(repository.findByName(randomHero)).thenReturn(Collections.emptyList());

        var fakeHero = service.findAll(randomHero);

        Assertions.assertThat(fakeHero).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByIdOrThrowNotFound returns a hero when id is found")
    @Order(4)
    void findByIdOrThrowNotFound_ReturnsHero_WhenIdIsFound() {
        var expectedHero = heroList.getFirst();

        BDDMockito.when(repository.findById(expectedHero.getId())).thenReturn(Optional.of(expectedHero));

        var heroFound = service.findByIdOrThrowNotFound(expectedHero.getId());

        Assertions.assertThat(heroFound).isEqualTo(expectedHero);
    }

    @Test
    @DisplayName("findByIdOrThrowNotFound throws ResponseStatusException when hero is not found")
    @Order(5)
    void findByIdOrThrowNotFound_ThrowsResponseStatusException_WhenNameIsNotFound() {
        var expectedHero = heroList.getFirst();

        BDDMockito.when(repository.findById(expectedHero.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedHero.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a new hero")
    @Order(6)
    void save_ReturnsHeroCreated_WhenSuccesfull() {
        var heroToSave = Hero.builder().id(10L).name("Bruno").createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(heroToSave)).thenReturn(heroToSave);

        var heroSaved = service.save(heroToSave);

        Assertions.assertThat(heroSaved).isEqualTo(heroToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a hero when found")
    @Order(7)
    void delete_RemovesHero_WhenFound() {
        var heroToDelete = heroList.getFirst();

        BDDMockito.when(repository.findById(heroToDelete.getId())).thenReturn(Optional.of(heroToDelete));
        BDDMockito.doNothing().when(repository).delete(heroToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(heroToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws ResponseStatusException when hero is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenHeroIsNotFound() {
        var heroToDelete = heroList.getFirst();

        BDDMockito.when(repository.findById(heroToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(heroToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("updates update hero in the list")
    @Order(9)
    void updates_UpdatesHero_WhenFound() {
        var heroToUpdate = heroList.getFirst();
        heroToUpdate.setName("Bruno");

        BDDMockito.when(repository.findById(heroToUpdate.getId())).thenReturn(Optional.of(heroToUpdate));
        BDDMockito.doNothing().when(repository).update(heroToUpdate);

        service.update(heroToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(heroToUpdate));
    }

    @Test
    @DisplayName("updates throws ResponseStatusException when hero is not found")
    @Order(10)
    void updates_ThrowsResponseStatusException_WhenHeroIsNotFound() {
        var heroToUpdate = heroList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(heroToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}
















