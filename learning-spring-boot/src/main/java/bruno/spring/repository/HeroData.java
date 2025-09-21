package bruno.spring.repository;

import bruno.spring.domain.Hero;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeroData {
    private final List<Hero> heroes = new ArrayList<>();

    {
        heroes.add(Hero.builder().id(1L).name("Doctor Strange").createdAt(LocalDateTime.now()).build());
        heroes.add(Hero.builder().id(2L).name("Black Panther").createdAt(LocalDateTime.now()).build());
        heroes.add(Hero.builder().id(3L).name("Iron Man").createdAt(LocalDateTime.now()).build());
    }

    public List<Hero> getHeroes() {
        return heroes;
    }
}
