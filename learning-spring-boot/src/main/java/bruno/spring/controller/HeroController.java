package bruno.spring.controller;

import bruno.spring.domain.Hero;
import bruno.spring.mapper.HeroMapper;
import bruno.spring.request.HeroPostRequest;
import bruno.spring.response.HeroGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/heroes")
@Slf4j
public class HeroController {
    private static final HeroMapper MAPPER = HeroMapper.INSTANCE;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HeroGetResponse> test(@RequestBody HeroPostRequest heroPostRequest) {
        Hero heroMapper = MAPPER.toHero(heroPostRequest);

        HeroGetResponse response = MAPPER.toHeroGetResponse(heroMapper);

        heroes.add(heroMapper);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}




















