package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.yellow.test.model.User;
import test.yellow.test.resourceassembler.UserResourceAssembler;
import test.yellow.test.security.TokenManager;
import test.yellow.test.service.user.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserResourceAssembler assembler;
    private final UserService userService;

    @Autowired
    UserController(
            final UserResourceAssembler assembler,
            final UserService userService
    ) {
        this.assembler = assembler;
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@Valid @RequestBody final User user) throws URISyntaxException, ParseException {
        final Resource<User> resource = assembler.toResource(userService.create(user));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@Valid @RequestBody final User user) {
        final Optional<User> existingUser = userService.getByLoginAndPassword(user.getLogin());

        if (!existingUser.isPresent()
                || !userService.isValidPassword(user.getPassword(), existingUser.get())) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(TokenManager.HEADER_STRING, userService.getToken(user.getLogin()));

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    public Resources<Resource<User>> all() {
        final List<Resource<User>> users = userService.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> one(@PathVariable final Long id) {
        final Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toResource(user.get()));
    }
}
