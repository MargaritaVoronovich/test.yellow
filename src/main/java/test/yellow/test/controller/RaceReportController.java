package test.yellow.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.yellow.test.model.WeeklyRaceReport;

import java.util.List;

@RestController
public class RaceReportController {
    private final WeeklyRaceReport report;

    @Autowired
    RaceReportController(final WeeklyRaceReport report) {
        this.report = report;
    }

    @GetMapping("/race-reports/weekly/{id}")
    public List<WeeklyRaceReport> all(@PathVariable final Long id) {
        //TODO: implement
        return report.get(id);
    }
}
