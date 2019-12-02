package com.lyx.las.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.lyx.las.helper.JsonViewHelper;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 参考 https://juejin.im/post/5c9193d9f265da60d63ea8dd
 */
public class User implements UserDetails {

    private int id;

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "name cannot be blank")
    private String name;

    private int role;

    private String accessToken;

    private List<Role> authorities;

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
    public List<Role> getAuthorities() {
        return null;
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
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @JsonView(JsonViewHelper.SimpleView.class)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }
}
