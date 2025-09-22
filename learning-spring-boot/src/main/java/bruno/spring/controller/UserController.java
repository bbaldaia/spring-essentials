package bruno.spring.controller;

import bruno.spring.domain.User;
import bruno.spring.mapper.UserMapper;
import bruno.spring.request.UserPostRequest;
import bruno.spring.request.UserPutRequest;
import bruno.spring.response.UserGetResponse;
import bruno.spring.response.UserPostResponse;
import bruno.spring.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String firstName) {
        log.debug("Iniciando captura de todos os usuários");

        List<User> users = service.findAll(firstName);

        List<UserGetResponse> response =  userMapper.toUserGetResponseList(users);

        log.debug("Captura finalizada. {} usuários encontrados", users.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.debug("Iniciando captura de usuário pelo id '{}'", id);

        User user = service.findById(id);

        UserGetResponse response = userMapper.toUserGetResponse(user);

        log.debug("Captura do usuário com id '{}' finalizada", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPostResponse> create(@RequestBody UserPostRequest request) {
        log.debug("Iniciando criação de usuário com os seguintes dados: '{}'", request);

        User user = userMapper.toUser(request);

        User userToSave = service.create(user);

        UserPostResponse response = userMapper.toUserPostResponse(userToSave);

        log.debug("Criação finalizada. Corpo da resposta: '{}'", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Iniciando exclusão de usuário com id '{}'", id);

        service.delete(id);

        log.debug("Exclusão de usuário com id '{}' finalizada", id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserPutRequest request) {
        log.debug("Iniciando atualização de usuário com id '{}'. Informações novas: {}", request.getId(), request);

        User userToUpdate = userMapper.toUser(request);

        service.update(userToUpdate);

        return ResponseEntity.noContent().build();
    }
}





















