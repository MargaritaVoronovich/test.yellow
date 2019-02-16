package test.yellow.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import test.yellow.test.service.listener.UserEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
@EntityListeners(UserEntityListener.class)

public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar", nullable = false, unique = true)
    @Email
    private String login;

    @Column(columnDefinition = "varchar", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Race> races;

    public AppUser() {
    }

    public AppUser(String login, String password, Race... races) {
        this.login = login;
        this.password = password;
        this.races = Stream.of(races).collect(Collectors.toSet());
        this.races.forEach(x -> x.setUser(this));
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        if (!id.equals(appUser.id)) return false;
        if (!login.equals(appUser.login)) return false;
        if (!password.equals(appUser.password)) return false;
        return races != null ? races.equals(appUser.races) : appUser.races == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (races != null ? races.hashCode() : 0);
        return result;
    }
}
