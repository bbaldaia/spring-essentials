package user.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import user.service.domain.User;
import user.service.request.UserPostRequest;
import user.service.request.UserPutRequest;
import user.service.response.UserGetResponse;
import user.service.response.UserPostResponse;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100))")
    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest userPutRequest);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

    UserGetResponse toUserGetResponse(User user);

    UserPostResponse toUserPostResponse(User user);
}