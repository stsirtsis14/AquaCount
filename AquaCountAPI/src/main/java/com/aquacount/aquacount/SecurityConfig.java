package com.aquacount.aquacount;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;


@Configuration
public class SecurityConfig{

    private final JwtUtil jwtTokenProvider;
    private UserDetailsService userDetailsService;
    public SecurityConfig(JwtUtil jwtTokenProvider,UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Απενεργοποίηση της αυθεντικοποίησης στην προεπιλεγμένη σελίδα εισόδου
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            //config.setAllowedOrigins(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setExposedHeaders(List.of("Authorization"));
            config.setMaxAge(3600L);

            return config;
        });

        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/login"}).permitAll();
        http.authorizeHttpRequests().requestMatchers(new String[]{"/routes/allRoutes"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/routes/register"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/clocks/register"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/clocks/deleteClock/{clockid}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/measurements/{measurementId}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/measurements/measurementOfaCounter/{counterid}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/register"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/allcounters"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/update/{counterid}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/delete/{counterid}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().requestMatchers(new String[]{"/counters/CounterByRouteid/{routeid}"}).hasAuthority("Admin");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider());



        // Προσθήκη του φίλτρου αυθεντικοποίησης πριν από το UsernamePasswordAuthenticationFilter
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}

