package bruno.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Hero {
    private Long id;
    @JsonProperty("hero_name")
    private String name;
    private static List<Hero> heroes = new ArrayList<>();

    static {
        heroes.add(new Hero(1L, "Hero 1"));
        heroes.add(new Hero(2L, "Hero 2"));
    }

    public static List<Hero> listAllHeros() {
        return heroes;
    }
}
