package test.yellow.test.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.yellow.test.repository.RaceRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeeklyRaceReport implements Serializable {
    public Long avgSpeed;
    public Long avgTime;
    public Long totalDistance;
    public Date weekStart;
    public Date weekEnd;

    private final RaceRepository repository;

    @Autowired
    public WeeklyRaceReport(RaceRepository repository) {
        this.repository = repository;
    }

    public List<WeeklyRaceReport> get(Long userId) {
        //TODO: implement
        return repository.getWeeklyReportByUserId(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeeklyRaceReport that = (WeeklyRaceReport) o;

        if (!avgSpeed.equals(that.avgSpeed)) return false;
        if (!avgTime.equals(that.avgTime)) return false;
        if (!totalDistance.equals(that.totalDistance)) return false;
        if (!weekStart.equals(that.weekStart)) return false;
        if (!weekEnd.equals(that.weekEnd)) return false;
        return repository != null ? repository.equals(that.repository) : that.repository == null;
    }

    @Override
    public int hashCode() {
        int result = avgSpeed.hashCode();
        result = 31 * result + avgTime.hashCode();
        result = 31 * result + totalDistance.hashCode();
        result = 31 * result + weekStart.hashCode();
        result = 31 * result + weekEnd.hashCode();
        result = 31 * result + (repository != null ? repository.hashCode() : 0);
        return result;
    }
}
