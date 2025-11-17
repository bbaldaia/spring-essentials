package bruno.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeroGetResponse {
    private Long id;
    @JsonProperty("hero_name")
    private String name;
}