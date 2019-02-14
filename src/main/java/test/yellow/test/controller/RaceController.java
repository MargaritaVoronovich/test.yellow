package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.yellow.test.model.Race;
import test.yellow.test.resourceassembler.RaceResourceAssembler;
import test.yellow.test.service.race.RaceService;

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
public class RaceController {

    private final RaceResourceAssembler assembler;
    private final RaceService raceService;

    @Autowired
    RaceController(
            final RaceResourceAssembler assembler,
            final RaceService raceService
    ) {
        this.assembler = assembler;
        this.raceService = raceService;
    }

    @GetMapping("/users/races")
    public Resources<Resource<Race>> all() {
        final List<Resource<Race>> races = raceService.getAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(races,
                linkTo(methodOn(RaceController.class).all()).withSelfRel());
    }

    @GetMapping("/users/races/{id}")
    public ResponseEntity<?> one(@PathVariable final Long id) {
        final Optional<Race> race = raceService.findById(id);

        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toResource(race.get()));
    }

    @PostMapping("/users/{id}/races")
    public ResponseEntity<?> create(@Valid @RequestBody final Race race, @PathVariable final Long id) throws URISyntaxException, ParseException {
        final Resource<Race> resource = assembler.toResource(raceService.create(race, id));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/users/races/{id}")
    ResponseEntity<?> edit(
            @Valid @RequestBody final Race raceFromJson,
            @PathVariable final Long id
    ) throws URISyntaxException {
        final Optional<Race> race = raceService.findById(id);

        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        final Resource<Race> resource = assembler.toResource(
                raceService.update(raceFromJson, race.get())
        );

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("users/races/{id}")
    ResponseEntity<?> delete(@PathVariable final Long id) {
        final Optional<Race> race = raceService.findById(id);

        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        raceService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
