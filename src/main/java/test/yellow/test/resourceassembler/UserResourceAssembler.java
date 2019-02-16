package test.yellow.test.resourceassembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import test.yellow.test.controller.UserController;
import test.yellow.test.model.AppUser;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<AppUser, Resource<AppUser>> {

    @Override
    public Resource<AppUser> toResource(final AppUser appUser) {
        return new Resource<>(appUser,
                linkTo(methodOn(UserController.class).one(appUser.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}
