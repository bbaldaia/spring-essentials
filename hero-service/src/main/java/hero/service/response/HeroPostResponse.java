package hero.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeroPostResponse {
    private Long id;
    private String name;
}
