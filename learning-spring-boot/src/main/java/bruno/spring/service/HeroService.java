package bruno.spring.service;

import bruno.spring.domain.Hero;
import bruno.spring.repository.HeroHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroService {
    private final HeroHardCodedRepository repository;

    public List<Hero> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Hero findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "HERO NOT FOUND!"));
    }

    public Hero save(Hero hero) {
        return repository.save(hero);
    }

    public void delete(Long id) {
        var hero = findByIdOrThrowNotFound(id);
        repository.delete(hero);
    }

    public void update(Hero heroToUpdate) {
        var hero = findByIdOrThrowNotFound(heroToUpdate.getId());
        heroToUpdate.setCreatedAt(hero.getCreatedAt());
        repository.update(heroToUpdate);
    }
}

















