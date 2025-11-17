package bruno.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import bruno.spring.domain.User;
import bruno.spring.request.UserPostRequest;
import bruno.spring.request.UserPutRequest;
import bruno.spring.response.UserGetResponse;
import bruno.spring.response.UserPostResponse;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest userPutRequest);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserGetResponse(User user);

    UserPostResponse toUserPostResponse(User user);
}