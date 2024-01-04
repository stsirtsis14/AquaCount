package com.aquacount.aquacount.model.security;

import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private CountersEntity countersEntity;
    public UserDetailsImpl(CountersEntity countersEntity) {
        this.countersEntity = countersEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(countersEntity.getAuthority()));
        return authorities;
    }

    @Override
    public String getPassword() {

        return countersEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return countersEntity.getUsername();
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
}
