package hero.service.mapper;

import hero.service.domain.Hero;
import hero.service.request.HeroPostRequest;
import hero.service.request.HeroPutRequest;
import hero.service.response.HeroGetResponse;
import hero.service.response.HeroPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HeroMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Hero toHero(HeroPostRequest heroPostRequest);

    Hero toHero(HeroPutRequest heroPutRequest);

    HeroGetResponse toHeroGetResponse(Hero hero);

    List<HeroGetResponse> toHeroGetResponseList(List<Hero> heroes);

    HeroPostResponse toHeroPostResponse(Hero hero);
}
