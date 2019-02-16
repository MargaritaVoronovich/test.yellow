package test.yellow.test.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.yellow.test.model.AppUser;
import test.yellow.test.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final AppUserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(
            AppUserRepository repository,
            BCryptPasswordEncoder encoder
    ) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public AppUser create(AppUser appUser) {
        return repository.save(appUser);
    }

    public Optional<AppUser> findById(final Long id) {
        return repository.findById(id);
    }

    public Optional<AppUser> getByLoginAndPassword(final String login) {
        return Optional.ofNullable(repository.findByLogin(login));
    }

    public List<AppUser> findAll() {
        return repository.findAll();
    }

    public boolean isValidPassword(String password, AppUser appUser) {
        return encoder.matches(password, appUser.getPassword());
    }
}
