package ru.zakharov.newcourse.domains;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.zakharov.newcourse.Role;
import ru.zakharov.newcourse.annotations.EmailCheck;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Transactional
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Укажите ваше имя!")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Используйте EN раскладку!")
    @Size.List({
            @Size(min = 3, message = "Минимальное количество символов {min}!"),
            @Size(max = 20, message = "Максимальное количество символов {max}!")
    })
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Укажите пароль!")
    @Size.List({
            @Size(min = 6, message = "Минимальная длина пароля: {min}"),
            @Size(max = 20, message = "Максимальная длина пароля: {max}")
    })
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Укажите свой e-mail!")
    @EmailCheck
    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Dictionary> dictionaries;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void addDictionary(Dictionary dictionary) {
        if (dictionaries == null) {
            dictionaries = new ArrayList<>();
        }
        dictionary.setUser(this);
        dictionaries.add(dictionary);
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
