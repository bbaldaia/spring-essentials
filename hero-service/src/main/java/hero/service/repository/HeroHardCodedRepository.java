package hero.service.repository;

import hero.service.domain.Hero;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import hero.service.config.Connection;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j2
public class HeroHardCodedRepository {
    private final HeroData heroData;
    private final Connection connection;

    public List<Hero> findAll() {
        log.info("connection -----> {}", connection);
        return heroData.getHeroes();
    }

    public Optional<Hero> findById(Long id) {
        return heroData.getHeroes().stream().filter(hero -> hero.getId().equals(id)).findFirst();
    }

    public List<Hero> findByName(String name) {
        return heroData.getHeroes().stream().filter(hero -> hero.getName().equalsIgnoreCase(name)).toList();
    }

    public Hero save(Hero hero) {
        heroData.getHeroes().add(hero);

        return hero;
    }

    public void delete(Hero hero) {
        heroData.getHeroes().remove(hero);
    }

    public void update(Hero hero) {
        delete(hero);
        save(hero);
    }
}