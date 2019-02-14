package test.yellow.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.yellow.test.model.Race;

import java.util.Optional;


@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Optional<Race> findByUserId(Long userId);

    Optional<Race> findByIdAndUserId(Long id, Long raceId);
}
