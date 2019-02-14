package test.yellow.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.yellow.test.model.Race;
import test.yellow.test.model.WeeklyRaceReport;

import java.util.List;
import java.util.Optional;


@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Optional<Race> findByUserId(Long userId);

    Optional<Race> findByIdAndUserId(Long id, Long raceId);

    //TODO: implement
    @Query(value = "SELECT avg_time, " +
            "avg_speed, " +
            "total_distance, " +
            "cast(date_trunc('week', max_date) as date) as week_start, " +
            "cast((date_trunc('week', max_date) + INTERVAL '6days') as date) as week_end " +
            "FROM (SELECT avg(race_time)as avg_time, " +
            "             sum(distance)             as total_distance, " +
            "             avg(distance / race_time) as avg_speed, " +
            "             max(race_date)            as max_date " +
            "      FROM races " +
            "      WHERE user_id = :userId " +
            "      GROUP BY extract(week from race_date)) as subquery", nativeQuery = true)
    List<WeeklyRaceReport> getWeeklyReportByUserId(@Param("userId") Long userId);
}
