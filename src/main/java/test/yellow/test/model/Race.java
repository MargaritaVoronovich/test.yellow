package test.yellow.test.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "races")
public class Race implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "integer", nullable = false)
    private Long distance;

    @Column(columnDefinition = "integer", nullable = false)
    private Long raceTime;

    @Column(columnDefinition = "date", nullable = false)
    private Date raceDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private AppUser user;

    public Race() {
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Race(
            Long distance,
            Long raceTime,
            Date raceDate
    ) {
        this.distance = distance;
        this.raceTime = raceTime;
        this.raceDate = raceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(Long raceTime) {
        this.raceTime = raceTime;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Race race = (Race) o;

        if (!id.equals(race.id)) return false;
        if (!distance.equals(race.distance)) return false;
        if (!raceTime.equals(race.raceTime)) return false;
        if (!raceDate.equals(race.raceDate)) return false;
        return user.equals(race.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + distance.hashCode();
        result = 31 * result + raceTime.hashCode();
        result = 31 * result + raceDate.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
