package com.aquacount.aquacount.service.measurement.impl;

import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import com.aquacount.aquacount.model.security.UserDetailsImpl;
import com.aquacount.aquacount.repository.CountersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  CountersRepository countersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CountersEntity counterEntity = countersRepository.findByUsername(username);
        if(counterEntity == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new UserDetailsImpl(counterEntity);
    }

}
