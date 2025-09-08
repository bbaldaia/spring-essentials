package bruno.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Hero {
    private Long id;
    @JsonProperty("hero_name")
    private String name;
    private LocalDateTime createdAt;
    private static List<Hero> heroes = new ArrayList<>();

    static {
        heroes.add(Hero.builder().id(1L).name("Hero 1").createdAt(LocalDateTime.now()).build());
    }

    public static List<Hero> listAllHeros() {
        return heroes;
    }
}
