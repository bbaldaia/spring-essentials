package bruno.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hero {
    @EqualsAndHashCode.Include
    private Long id;
    @JsonProperty("hero_name")
    private String name;
    private LocalDateTime createdAt;
}
