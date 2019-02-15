package test.yellow.test.model;


import java.io.Serializable;
import java.util.Date;

public class WeeklyRaceReport implements Serializable {
    private Long avgSpeed;
    private Long avgTime;
    private Long totalDistance;
    private Date weekStart;
    private Date weekEnd;

    public WeeklyRaceReport(Long avgTime, Long avgSpeed, Long totalDistance, Date weekStart, Date weekEnd) {
        this.avgSpeed = avgSpeed;
        this.avgTime = avgTime;
        this.totalDistance = totalDistance;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
    }

    public Long getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    public Long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Date getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(Date weekStart) {
        this.weekStart = weekStart;
    }

    public Date getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(Date weekEnd) {
        this.weekEnd = weekEnd;
    }

    public Long getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Long avgSpeed) {
        this.avgSpeed = avgSpeed;
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
        return weekEnd.equals(that.weekEnd);
    }

    @Override
    public int hashCode() {
        int result = avgSpeed.hashCode();
        result = 31 * result + avgTime.hashCode();
        result = 31 * result + totalDistance.hashCode();
        result = 31 * result + weekStart.hashCode();
        result = 31 * result + weekEnd.hashCode();
        return result;
    }
}
