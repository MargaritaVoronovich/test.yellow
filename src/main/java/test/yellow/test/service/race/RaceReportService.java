package test.yellow.test.service.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.yellow.test.model.WeeklyRaceReport;
import test.yellow.test.repository.RaceReportRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class RaceReportService {
    private final RaceReportRepository raceReportRepository;
    private final EntityManager entityManager;

    @Autowired
    public RaceReportService(
            RaceReportRepository raceReportRepository,
            EntityManager entityManager
    ) {
        this.raceReportRepository = raceReportRepository;
        this.entityManager = entityManager;
    }

    public List<WeeklyRaceReport> getWeeklyReport(Long userId) {
        return raceReportRepository.getWeeklyReportByUserId(userId, entityManager);
    }
}
