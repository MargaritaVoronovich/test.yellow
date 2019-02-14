package test.yellow.test.resourceassembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import test.yellow.test.controller.RaceController;
import test.yellow.test.model.Race;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RaceResourceAssembler implements ResourceAssembler<Race, Resource<Race>> {

    @Override
    public Resource<Race> toResource(final Race race) {
        return new Resource<>(race,
                linkTo(methodOn(RaceController.class).one(race.getId())).withSelfRel(),
                linkTo(methodOn(RaceController.class).all()).withRel("races"));
    }
}
