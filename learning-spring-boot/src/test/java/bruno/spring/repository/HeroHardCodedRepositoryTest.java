package bruno.spring.repository;

import bruno.spring.domain.Hero;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HeroHardCodedRepositoryTest {

    @InjectMocks
    private HeroHardCodedRepository repository;
    @Mock
    private HeroData heroData;
    private final List<Hero> heroList = new ArrayList<>();

    @BeforeEach
    void init() {
        heroList.add(Hero.builder().id(1L).name("Doctor Strange").createdAt(LocalDateTime.now()).build());
        heroList.add(Hero.builder().id(2L).name("Black Panther").createdAt(LocalDateTime.now()).build());
    }

    @Test
    @DisplayName("findAll returns a list of all heroes found")
    @Order(1)
    void findAll_ReturnsAllHeroes_WhenSuccessful() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroes = repository.findAll();
        Assertions.assertThat(heroes).isNotNull().hasSameElementsAs(heroList);
    }

    @Test
    @DisplayName("findById returns a hero based on the id")
    @Order(2)
    void findById_ReturnsHeroById_WhenSuccessful() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var expectedHero = heroList.getFirst();
        var heroById = repository.findById(expectedHero.getId());

        Assertions.assertThat(heroById).isPresent().contains(expectedHero);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroByName = repository.findByName(null);

        Assertions.assertThat(heroByName).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns the hero found when name is not null")
    @Order(4)
    void findByName_ReturnsFoundHeroInList_WhenNameIsFound() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var expectedHero = heroList.getFirst();
        var heroByName = repository.findByName(expectedHero.getName());

        Assertions.assertThat(heroByName).contains(expectedHero);
    }

    @Test
    @DisplayName("save creates a hero in the list")
    @Order(5)
    void save_createsHeroInTheList_WhenSuccessful() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroToSave = Hero.builder().id(10L).name("Bruno").createdAt(LocalDateTime.now()).build();
        var hero = repository.save(heroToSave);

        Assertions.assertThat(hero).isEqualTo(heroToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete removes a hero from list")
    @Order(6)
    void delete_RemoveHero_WhenSuccessful() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroToDelete = heroList.getFirst();
        repository.delete(heroToDelete);

        Assertions.assertThat(heroList).isNotEmpty().doesNotContain(heroToDelete);
    }

    @Test
    @DisplayName("update method updates a hero in the list")
    @Order(7)
    void update_UpdateHero_WhenSuccessful() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroToUpdate = heroList.getFirst();
        heroToUpdate.setName("Black Panther");

        repository.update(heroToUpdate);

        Assertions.assertThat(heroList).contains(heroToUpdate);

        var optionalUpdatedHero = repository.findById(heroToUpdate.getId());

        Assertions.assertThat(optionalUpdatedHero).isPresent();
        Assertions.assertThat(optionalUpdatedHero.get().getName()).isEqualTo(heroToUpdate.getName());
    }
}