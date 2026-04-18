package com.oscaris.caterers.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_users")
//@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true,nullable = false)
    private String username; // username should be the email by default
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(nullable = false)
    private boolean enabled = false;

    @Embedded
    private UserInfo userInfo;

    public User(String username, String password, String email, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userInfo = userInfo;
    }


    public User(Long userId, String username, String email, String password, boolean enabled, UserInfo userInfo) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.userInfo = userInfo;
    }

    public User(Long userId, String username, String email, String password, boolean enabled, UserInfo userInfo,
                List<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.userInfo = userInfo;
        this.roles = roles;
    }

    public User() {
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return roles.stream()
                .map( role-> (GrantedAuthority) () -> role.getName())
                .toList();
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return  true;
    }
    @Override
    public boolean isEnabled(){
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", student=" + userInfo +
                '}';
    }


}
