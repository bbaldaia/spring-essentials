package user.service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserPostRequest {
    @NotBlank(message = "FIRST NAME IS MANDATORY!")
    private String firstName;
    @NotBlank(message = "LAST NAME IS MANDATORY!")
    private String lastName;
    @Email(message = "E-MAIL IS INVALID!")
    @NotBlank(message = "E-MAIL IS MANDATORY!")
    private String email;
}