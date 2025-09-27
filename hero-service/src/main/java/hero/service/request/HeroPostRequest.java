package hero.service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeroPostRequest {
    @NotBlank(message = "NAME IS MANDATORY!")
    private String name;
}
