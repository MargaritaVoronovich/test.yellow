package test.yellow.test.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.yellow.test.model.User;
import test.yellow.test.repository.UserRepository;
import test.yellow.test.security.TokenManager;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final TokenManager tokenManager;

    @Autowired
    public UserService(
            UserRepository repository,
            BCryptPasswordEncoder encoder,
            TokenManager tokenManager
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenManager = tokenManager;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public Optional<User> findById(final Long id) {
        return repository.findById(id);
    }

    public Optional<User> getByLoginAndPassword(final String login) {
        return Optional.ofNullable(repository.findByLogin(login));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public boolean isValidPassword(String password, User user) {
        return encoder.matches(password, user.getPassword());
    }

    public String getToken(String login) {
        return tokenManager.create(login);
    }
}
