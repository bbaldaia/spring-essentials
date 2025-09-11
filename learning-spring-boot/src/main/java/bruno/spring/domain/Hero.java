package bruno.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hero {
    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty("hero_name")
    private String name;
    private LocalDateTime createdAt;
    private static List<Hero> heroes = new ArrayList<>();

    static {
        heroes.add(Hero.builder().id(1L).name("Doctor Strange").createdAt(LocalDateTime.now()).build());
    }

    public static List<Hero> listAllHeros() {
        return heroes;
    }
}
