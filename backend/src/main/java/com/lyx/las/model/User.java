package com.lyx.las.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.lyx.las.helper.JsonViewHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考 https://juejin.im/post/5c9193d9f265da60d63ea8dd
 */
public class User implements UserDetails, Serializable {

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_TEACHER = "Teacher";
    public static final String ROLE_STUDENT = "Student";
    public static final String ROLE_INSTRUCTOR = "Instructor";

    private int id;

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "name cannot be blank")
    private String name;

    private String role;

    private String accessToken;

    private List<SimpleGrantedAuthority> authorities;

    @JsonView(JsonViewHelper.SimpleView.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonView(JsonViewHelper.SimpleView.class)
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {

        if (this.authorities == null) {
            this.authorities = new ArrayList<>();
            this.authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));
        }
        return this.authorities;
    }

    @JsonView(JsonViewHelper.DetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(JsonViewHelper.SimpleView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(JsonViewHelper.SimpleView.class)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonView(JsonViewHelper.DetailView.class)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "(" + id + "," + username + "," + password + "," + name + "," + role + "," + accessToken + ")";
     }
}
