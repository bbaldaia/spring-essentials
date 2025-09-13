package bruno.spring.mapper;

import bruno.spring.domain.Hero;
import bruno.spring.request.HeroPostRequest;
import bruno.spring.request.HeroPutRequest;
import bruno.spring.response.HeroGetResponse;
import bruno.spring.response.HeroPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HeroMapper {

    HeroMapper INSTANCE = Mappers.getMapper(HeroMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Hero toHero(HeroPostRequest heroPostRequest);

    Hero toHero(HeroPutRequest heroPutRequest);

    HeroGetResponse toHeroGetResponse(Hero hero);

    List<HeroGetResponse> toHeroGetResponseList(List<Hero> heroes);

    HeroPostResponse toHeroPostResponse(Hero hero);
}
