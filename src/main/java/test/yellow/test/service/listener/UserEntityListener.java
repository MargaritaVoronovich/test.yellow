package test.yellow.test.service.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import test.yellow.test.model.AppUser;

import javax.persistence.PrePersist;

@Component
public class UserEntityListener {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserEntityListener(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PrePersist
    public void beforeSave(final AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
    }
}
