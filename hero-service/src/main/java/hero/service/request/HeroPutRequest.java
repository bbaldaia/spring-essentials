package hero.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroPutRequest {
    private Long id;
    private String name;
}
