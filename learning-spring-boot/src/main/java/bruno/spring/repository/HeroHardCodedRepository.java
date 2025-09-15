package bruno.spring.repository;

import bruno.spring.domain.Hero;
import external.dependency.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j2
public class HeroHardCodedRepository {

    private static final List<Hero> HEROES = new ArrayList<>();
    private final Connection connect;

    static {
        HEROES.add(Hero.builder().id(1L).name("Doctor Strange").createdAt(LocalDateTime.now()).build());
    }

    public List<Hero> findAll() {
        log.debug(connect);
        return HEROES;
    }

    public Optional<Hero> findById(Long id) {
        return HEROES.stream().filter(hero -> hero.getId().equals(id)).findFirst();
    }

    public List<Hero> findByName(String name) {
        return HEROES.stream().filter(hero -> hero.getName().equalsIgnoreCase(name)).toList();
    }

    public Hero save(Hero hero) {
        HEROES.add(hero);

        return hero;
    }

    public void delete(Hero hero) {
        HEROES.remove(hero);
    }

    public void update(Hero hero) {
        delete(hero);
        save(hero);
    }
}
