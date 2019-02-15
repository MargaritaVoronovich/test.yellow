package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import test.yellow.test.model.WeeklyRaceReport;
import test.yellow.test.service.race.RaceService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceReportController {
    private final RaceService raceService;

    @Autowired
    RaceReportController(final RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/race-reports/weekly/{id}")
    public List<WeeklyRaceReport> weekly(@PathVariable final Long id) {
//    public Resources<List<WeeklyRaceReport>> weekly(@PathVariable final Long id) {
        return raceService.getWeeklyReport(id);

        //TODO: format to HAL
        /*final List<Resource<WeeklyRaceReport>> report = raceService.getWeeklyReport(id).stream()
                .map(x -> new Resource<>(x))
                .collect(Collectors.toList());

        return new Resources<>(report,
                linkTo(methodOn(RaceReportController.class).weekly(id)).withSelfRel());*/
    }
}
