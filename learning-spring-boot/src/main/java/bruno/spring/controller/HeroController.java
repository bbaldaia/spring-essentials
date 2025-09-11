package bruno.spring.controller;

import bruno.spring.domain.Hero;
import bruno.spring.mapper.HeroMapper;
import bruno.spring.request.HeroPostRequest;
import bruno.spring.request.HeroPutRequest;
import bruno.spring.response.HeroGetResponse;
import bruno.spring.response.HeroPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/heroes")
@Slf4j
public class HeroController {
    private static final HeroMapper MAPPER = HeroMapper.INSTANCE;
    private static List<Hero> heroes = new ArrayList<>(Hero.listAllHeros());

    @GetMapping
    public ResponseEntity<List<HeroGetResponse>> listAllHeros(@RequestParam(required = false) String name) {
        if (name == null) {
            log.debug("Request to fetch all heroes");
        } else {
            log.debug("Request to find hero '{}' by the name", name);
        }

        var heroGetResponseList = MAPPER.toHeroGetResponseList(heroes);

        if (name == null) {
            return ResponseEntity.ok(heroGetResponseList);
        }

        var response = heroGetResponseList.stream().filter(hero -> hero.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<HeroGetResponse> getById(@PathVariable Long id) {
        log.debug("Request to find hero by id '{}'", id);

        return ResponseEntity
                .ok()
                .body(heroes
                        .stream()
                        .filter(hero -> hero.getId().equals(id))
                        .findFirst()
                        .map(MAPPER::toHeroGetResponse)
                        .orElse(null));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HeroPostResponse> save(@RequestBody HeroPostRequest request) {
        log.debug("Request to save hero '{}'", request.getName());

        Hero heroMapper = MAPPER.toHero(request);

        heroes.add(heroMapper);

        HeroPostResponse response = MAPPER.toHeroPostResponse(heroMapper);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete id '{}'", id);

        var idToDelete = heroes
                .stream()
                .filter(hero -> hero.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This hero does not exist!"));

        heroes.remove(idToDelete);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody HeroPutRequest request) {
        var heroToDelete = heroes
                .stream()
                .filter(hero -> hero.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This hero does not exist!"));

        log.debug("Request to update from '{}' to '{}'", heroToDelete.getName(), request.getName());

        heroes.remove(heroToDelete);

        var heroToAdd = MAPPER.toHero(request, heroToDelete.getCreatedAt());

        heroes.add(heroToAdd);

        return ResponseEntity.noContent().build();
    }

}




















