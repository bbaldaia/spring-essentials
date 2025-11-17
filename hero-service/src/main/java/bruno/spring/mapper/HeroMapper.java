package bruno.spring.mapper;

import bruno.spring.domain.Hero;
import bruno.spring.request.HeroPostRequest;
import bruno.spring.request.HeroPutRequest;
import bruno.spring.response.HeroGetResponse;
import bruno.spring.response.HeroPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HeroMapper {

    Hero toHero(HeroPostRequest heroPostRequest);

    Hero toHero(HeroPutRequest heroPutRequest);

    HeroGetResponse toHeroGetResponse(Hero hero);

    List<HeroGetResponse> toHeroGetResponseList(List<Hero> heroes);

    HeroPostResponse toHeroPostResponse(Hero hero);
}