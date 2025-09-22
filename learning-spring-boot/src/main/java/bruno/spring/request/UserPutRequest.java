package bruno.spring.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserPutRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
