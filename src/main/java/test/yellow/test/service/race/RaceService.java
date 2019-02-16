package test.yellow.test.service.race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.yellow.test.model.Race;
import test.yellow.test.repository.RaceRepository;
import test.yellow.test.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RaceService {
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;

    @Autowired
    public RaceService(
            RaceRepository raceRepository,
            UserRepository userRepository
    ) {
        this.raceRepository = raceRepository;
        this.userRepository = userRepository;
    }

    public Race create(Race race, Long userId) {
        return userRepository.findById(userId).map(user -> {
            race.setUser(user);
            return raceRepository.save(race);
        }).orElseThrow(() -> new EntityNotFoundException("UserId " + userId + " not found"));
    }

    public Optional<Race> findById(final Long id) {
        return raceRepository.findById(id);
    }

    public void deleteById(final Long id) {
        raceRepository.deleteById(id);
    }

    public List<Race> getAll() {
        return raceRepository.findAll();
    }

    public Race update(Race raceFromJson, Race race) {
        race.setDistance(raceFromJson.getDistance());
        race.setRaceTime(raceFromJson.getRaceTime());
        race.setRaceDate(raceFromJson.getRaceDate());

        return raceRepository.save(race);
    }
}
