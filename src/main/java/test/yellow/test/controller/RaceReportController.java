package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import test.yellow.test.model.WeeklyRaceReport;
import test.yellow.test.service.race.RaceReportService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceReportController {
    private final RaceReportService raceReportService;

    @Autowired
    RaceReportController(final RaceReportService raceReportService) {
        this.raceReportService = raceReportService;
    }

    @GetMapping("/race-reports/weekly/{id}")
    public Resources<Resource<WeeklyRaceReport>> weekly(@PathVariable final Long id) {
        final List<Resource<WeeklyRaceReport>> report = raceReportService.getWeeklyReport(id).stream()
                .map(x -> new Resource<>(x))
                .collect(Collectors.toList());

        return new Resources<>(report,
                linkTo(methodOn(RaceReportController.class).weekly(id)).withSelfRel());
    }
}
