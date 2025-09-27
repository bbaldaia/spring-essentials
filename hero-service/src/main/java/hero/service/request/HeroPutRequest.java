package hero.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroPutRequest {
    @NotNull(message = "ID CAN'T BE NULL!")
    private Long id;
    @NotBlank(message = "NAME IS MANDATORY!")
    private String name;
}
