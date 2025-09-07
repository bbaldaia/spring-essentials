package bruno.spring.controller;

import bruno.spring.domain.Hero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/heroes")
@Slf4j
public class HeroController {
    private static List<Hero> heroes = new ArrayList<>(Hero.listAllHeros());

    @GetMapping
    public List<Hero> listAllHeros() {
        return heroes;
    }

    @PostMapping
    public Hero saveHero(@RequestBody Hero hero) {
        hero.setId(ThreadLocalRandom.current().nextLong(100));
        heroes.add(hero);

        return hero;
    }

    @PostMapping(value = "/testingHeaders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hero> test(@RequestBody Hero hero) {

        return ResponseEntity.status(HttpStatus.CREATED).body(hero);
    }
}




















