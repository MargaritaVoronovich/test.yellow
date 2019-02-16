package test.yellow.test.service.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.yellow.test.model.AppUser;
import test.yellow.test.repository.AppUserRepository;

import static java.util.Collections.emptyList;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser applicationAppUser = appUserRepository.findByLogin(username);

        if (applicationAppUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(applicationAppUser.getLogin(), applicationAppUser.getPassword(), emptyList());
    }
}
