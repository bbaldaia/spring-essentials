package bruno.spring.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
