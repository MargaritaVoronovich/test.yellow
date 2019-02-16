package test.yellow.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.yellow.test.model.AppUser;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByLogin(String login);
}
