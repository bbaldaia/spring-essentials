package bruno.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HeroPostResponse {
    private Long id;
    @JsonProperty("hero_name")
    private String name;
    private LocalDateTime createdAt;
}
