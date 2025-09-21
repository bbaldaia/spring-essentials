package bruno.spring.commons;

import bruno.spring.domain.Hero;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeroUtils {

    public List<Hero> newHeroList() {
        var dateTime = "2025-09-18T20:39:08.2228625";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var hulk = Hero.builder().id(1L).name("Hulk").createdAt(localDateTime).build();
        var thanos = Hero.builder().id(2L).name("Thanos").createdAt(localDateTime).build();

        return new ArrayList<>(List.of(hulk, thanos));
    }
}
