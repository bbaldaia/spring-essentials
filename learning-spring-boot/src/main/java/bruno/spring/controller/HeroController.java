package bruno.spring.controller;

import bruno.spring.domain.Hero;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {
    private static final List<String> HEROES = List.of("Hero1", "Hero2", "Hero3");

    @GetMapping("listAll")
    public List<String> listAllHeroes() {
        return HEROES;
    }

    @GetMapping("filter")
    public List<String> listHeroesFilter(@RequestParam String name) {
        return HEROES.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterParam")
    public List<String> listHeroesFilterParam(@RequestParam List<String> name) {
        return HEROES.stream().filter(name::contains).toList();
    }

    @GetMapping("filterParam/{hero}")
    public String findByHero(@PathVariable String hero) {
        return HEROES
                .stream()
                .filter(x -> x.equalsIgnoreCase(hero))
                .findFirst().orElse("CAN'T FIND THIS!");
    }

    @GetMapping("filterByHeroName")
    public List<Hero> findByHeroName(@RequestParam(required = false) String name) {
        if (name == null) {
            return Hero.listHeroes();
        } else {
            return Hero.listHeroes()
                    .stream()
                    .filter(hero -> hero.getName().equalsIgnoreCase(name))
                    .toList();
        }
    }

    @GetMapping("filterByHeroId/{id}")
    public Hero findByHeroId(@PathVariable long id) {
        return Hero.listHeroes()
                .stream()
                .filter(hero -> hero.getId().equals(id))
                .findFirst().orElse(null);
    }
}




















