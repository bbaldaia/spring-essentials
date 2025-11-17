package bruno.spring.commons;

import bruno.spring.domain.Hero;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeroUtils {

    public List<Hero> newHeroList() {
        var hulk = Hero.builder().id(1L).name("Hulk").build();
        var thanos = Hero.builder().id(2L).name("Thanos").build();

        return new ArrayList<>(List.of(hulk, thanos));
    }
}