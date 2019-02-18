package test.yellow.test.repository;


import org.springframework.stereotype.Repository;
import test.yellow.test.model.WeeklyRaceReport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class RaceReportRepository {
    private static WeeklyRaceReport toWeeklyReport(Object[] obj) {
        return new WeeklyRaceReport(
                (Float) obj[0],
                (Float) obj[1],
                ((BigInteger) obj[2]).longValue(),
                (Date) obj[3],
                (Date) obj[4]
        );
    }

    public List<WeeklyRaceReport> getWeeklyReportByUserId(Long userId, EntityManager entityManager) {
        final String queryStr = "SELECT cast(avg_time as real), " +
                "cast(avg_speed as real), " +
                "total_distance, " +
                "cast(date_trunc('week', max_date) as date) as week_start, " +
                "cast((date_trunc('week', max_date) + INTERVAL '6days') as date) as week_end " +
                "FROM (SELECT avg(race_time) as avg_time, " +
                "             sum(distance)             as total_distance, " +
                "             avg(cast(distance as real) / cast(race_time as real)) as avg_speed, " +
                "             max(race_date)            as max_date " +
                "      FROM races " +
                "      WHERE user_id = ?1 " +
                "      GROUP BY extract(week from race_date)) as subquery";

        final Query query = entityManager
                .createNativeQuery(queryStr)
                .setParameter(1, userId);

        final List<Object> result = query.getResultList();

        return result.stream()
                .map(o -> (Object[]) o)
                .map(RaceReportRepository::toWeeklyReport)
                .collect(Collectors.toList());
    }
}
