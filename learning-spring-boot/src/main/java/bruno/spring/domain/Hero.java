package bruno.spring.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Hero {
    private Long id;
    private String name;

    public Hero(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Hero> listHeroes() {
        return List.of
                (new Hero(1, "Goku"),
                 new Hero(2, "Vegeta"),
                 new Hero(3, "Gohan")
                );
    }
}
