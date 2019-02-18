package test.yellow.test.service.race;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import test.yellow.test.model.AppUser;
import test.yellow.test.model.Race;
import test.yellow.test.model.WeeklyRaceReport;
import test.yellow.test.service.user.UserService;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RaceReportServiceTest {

    private static final int YEAR = 2019;
    private static final int MONTH = 2;

    private Long userId;

    @Autowired
    private UserService userService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceReportService raceReportService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void before() {
        deleteFromTables(jdbcTemplate, "races", "users");
        createUser();
        createRaces();
    }

    @Test
    public void testGetWeeklyReport() {
        List<WeeklyRaceReport> weeklyRaceReports = raceReportService.getWeeklyReport(userId);

        WeeklyRaceReport week1 = weeklyRaceReports.get(0);
        WeeklyRaceReport week2 = weeklyRaceReports.get(1);

        assertThat(week1.getWeekStart()).isEqualTo(Date.valueOf(LocalDate.of(YEAR, MONTH, 4)));
        assertThat(week1.getWeekEnd()).isEqualTo(Date.valueOf(LocalDate.of(YEAR, MONTH, 10)));
        assertThat(week1.getTotalDistance()).isEqualTo(40);
        assertThat(week1.getAvgTime()).isEqualTo(30f);
        assertThat(week1.getAvgSpeed()).isEqualTo(0.625f);

        assertThat(week2.getWeekStart()).isEqualTo(Date.valueOf(LocalDate.of(YEAR, MONTH, 11)));
        assertThat(week2.getWeekEnd()).isEqualTo(Date.valueOf(LocalDate.of(YEAR, MONTH, 17)));
        assertThat(week2.getTotalDistance()).isEqualTo(120);
        assertThat(week2.getAvgTime()).isEqualTo(70f);
        assertThat(week2.getAvgSpeed()).isEqualTo(0.8541666f);
    }

    private void createUser() {
        AppUser appUser = new AppUser("test@user.test", "testPassword");
        AppUser newUser = userService.create(appUser);

        userId = newUser.getId();
    }

    private void createRaces() {
        //week1 (2019-02-04 - 2019-02-10)
        Race race1 = new Race(10L, 20L, Date.valueOf(LocalDate.of(YEAR, MONTH, 5)));
        Race race2 = new Race(30L, 40L, Date.valueOf(LocalDate.of(YEAR, MONTH, 9)));

        //week2 (2019-02-11 - 2019-02-17)
        Race race11 = new Race(50L, 60L, Date.valueOf(LocalDate.of(YEAR, MONTH, 12)));
        Race race22 = new Race(70L, 80L, Date.valueOf(LocalDate.of(YEAR, MONTH, 16)));

        List<Race> raceList = new ArrayList<>();

        raceList.add(race1);
        raceList.add(race2);
        raceList.add(race11);
        raceList.add(race22);

        raceList.forEach(r -> raceService.create(r, userId));
    }
}
