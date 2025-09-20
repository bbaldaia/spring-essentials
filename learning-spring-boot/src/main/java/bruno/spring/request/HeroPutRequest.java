package bruno.spring.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroPutRequest {
    private Long id;
    private String name;
}
