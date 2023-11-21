package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

//Я, конечно, всё равно это всё переделывал по этой задаче, но в чем смысл говорить, что у пользователя могут быть
//любые поля, если потом по заданию всё равно нужен возраст? (по скриншотам из Bootstrapа)

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private Integer age;
    @Column
    @Email
    private String email;

    @Column
    private String password;
    //в комментариях писали, что требуют обязательно FetchType.LAZY, хотя конкретно в этом случае я не очень понимаю,
    //в чем разница, потому что мы и так и так рассматриваем все поля ролей, т.е. и ленивая и жадная выглядят одинаково,
    //хотя я, может быть, что-то не так понял...
    @Column
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;


    public User() {
    }

    public User(Integer id, String username, Integer age, String email, String password) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User(String username, Integer age, String email, String password, List<Role> roles) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {

        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public String roleToString(){
        List<Role> roleList = getRoles();
        StringBuilder str = new StringBuilder();
        for(Role role: roleList){
            str.append(role.toString()).append(" ");
        }
        return str.toString();
    }

}
