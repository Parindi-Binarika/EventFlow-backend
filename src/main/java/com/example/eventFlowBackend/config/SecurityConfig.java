package com.example.eventFlowBackend.config;

import com.example.eventFlowBackend.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Authentication Endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // User Endpoints
                        .requestMatchers("/api/users/{id}").hasAnyRole("student", "lecturer", "admin")
                        .requestMatchers("/api/users/search/{nic}").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/users/**").hasRole("admin")

                        // Batch Endpoints
                        .requestMatchers("/api/batches/{id}").hasAnyRole("student", "lecturer", "admin")
                        .requestMatchers("/api/batches/allStudents/{bID}").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/batches/allBatches/{uID}").hasAnyRole("student", "lecturer", "admin")
                        .requestMatchers("/api/batches/**").hasRole("admin")

                        // Announcement Endpoints
                        .requestMatchers("/api/announcements/{aid}").hasAnyRole("admin", "student", "lecturer")
                        .requestMatchers("/api/announcements/created_by/{uid}").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/announcements/assigned/**").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/announcements/assigned/announcement/**").hasAnyRole("student", "admin")
                        .requestMatchers("/api/announcements/**").hasRole("admin")

                        // Event Endpoints
                        .requestMatchers("/api/event/interview/**", "/api/event/workshop/**").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/event/attendance/{eID}").hasRole("admin")
                        .requestMatchers("/api/event/{eID}", "/api/event/create/announcement/{eID}").hasAnyRole("lecturer", "admin")
                        .requestMatchers("/api/event/attendance/**", "/api/event/complete/{eID}").hasAnyRole("lecturer", "admin")

                        // Feedback Endpoints
                        .requestMatchers("/api/feedback/{eID}/{uID}").hasAnyRole("student", "admin")
                        .requestMatchers("/api/feedback/**").hasAnyRole("lecturer", "admin")

                        // Suggestion Endpoints
                        .requestMatchers("/api/suggestions/**").hasAnyRole("student", "lecturer", "admin")

                        // Portfolio and Project Endpoints
                        .requestMatchers("/api/portfolios/public/**").permitAll()
                        .requestMatchers("/api/portfolios/**", "/api/projects/**").hasAnyRole("student", "admin")

                        // Catch-All Rule
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
