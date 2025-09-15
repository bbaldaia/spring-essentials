package bruno.spring.repository;

import bruno.spring.domain.Hero;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HeroHardCodedRepositoryTest {

    @InjectMocks
    private HeroHardCodedRepository repository;
    @Mock
    private HeroData heroData;
    private final List<Hero> heroList = new ArrayList<>();

    @BeforeEach
    void init() {
        heroList.add(Hero.builder().id(1L).name("Doctor Strange").createdAt(LocalDateTime.now()).build());
    }

    @Test
    @DisplayName("findAll returns a list of all heroes found")
    void findAll_ReturnAllHeros_WhenSuccessfull() {
        BDDMockito.when(heroData.getHeroes()).thenReturn(heroList);

        var heroes = repository.findAll();
        Assertions.assertThat(heroes).isNotNull().hasSize(heroList.size());
    }




}