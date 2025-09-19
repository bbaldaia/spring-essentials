package bruno.spring.controller;

import bruno.spring.mapper.HeroMapper;
import bruno.spring.request.HeroPostRequest;
import bruno.spring.request.HeroPutRequest;
import bruno.spring.response.HeroGetResponse;
import bruno.spring.response.HeroPostResponse;
import bruno.spring.service.HeroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
@Slf4j
@RequiredArgsConstructor
public class HeroController {
    private final HeroMapper mapper;
    private final HeroService service;

    @GetMapping
    public ResponseEntity<List<HeroGetResponse>> findAllHeroes(@RequestParam(required = false) String name) {
        if (name == null) {
            log.debug("Request to fetch all heroes");
        } else {
            log.debug("Request to find hero '{}' by the name", name);
        }

        var heroes = service.findAll(name);

        var heroGetResponseList = mapper.toHeroGetResponseList(heroes);

        return ResponseEntity.ok(heroGetResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<HeroGetResponse> getById(@PathVariable Long id) {
        log.debug("Request to find hero by id '{}'", id);

        var hero = service.findByIdOrThrowNotFound(id);

        var heroGetResponse = mapper.toHeroGetResponse(hero);

        return ResponseEntity.ok(heroGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HeroPostResponse> save(@RequestBody HeroPostRequest request) {
        log.debug("Request to save hero '{}'", request.getName());

        var hero = mapper.toHero(request);

        var heroToBeAdded = service.save(hero);

        HeroPostResponse heroPostResponse = mapper.toHeroPostResponse(heroToBeAdded);

        return ResponseEntity.status(HttpStatus.CREATED).body(heroPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete id '{}'", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody HeroPutRequest request) {
        var heroToBeUpdated = mapper.toHero(request);

        service.update(heroToBeUpdated);

        return ResponseEntity.noContent().build();
    }
}