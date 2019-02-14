package test.yellow.test.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.yellow.test.model.User;
import test.yellow.test.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public Optional<User> getById(final Long id) {
        return repository.findById(id);
    }

    public Optional<User> getByLoginAndPassword(final String login) {
        return Optional.ofNullable(repository.findByLogin(login));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public boolean isValidPassword(String password, User user) {
        return encoder.matches(password, user.getPassword());
    }
}
