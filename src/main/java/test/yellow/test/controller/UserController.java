package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.yellow.test.model.AppUser;
import test.yellow.test.resourceassembler.UserResourceAssembler;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody final AppUser appUser) throws URISyntaxException, ParseException {
        final Resource<AppUser> resource = assembler.toResource(userService.create(appUser));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/users")
    public Resources<Resource<AppUser>> all() {
        final List<Resource<AppUser>> users = userService.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> one(@PathVariable final Long id) {
        final Optional<AppUser> user = userService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toResource(user.get()));
    }
}
