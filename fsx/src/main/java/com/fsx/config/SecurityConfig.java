package com.fsx.config;

import com.fsx.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//disabling csrf so that we can do post, put and delete ops without any problems
                //or
                //.csrf(ref -> ref.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/passenger/post").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/auth/user-details").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/schedule/get-schedule/by_source_destination/v2").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/schedule/get-schedule/by_source_destination").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/booking/add").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.POST, "/api/schedule/add").hasAnyAuthority("BUS_OPERATOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/bus/add").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.POST, "/api/operator/add").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/route/add").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/booking/get-one/{id}").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.GET, "/api/booking/getAllByPassenger").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.GET, "/api/bus/getAllByBusOperator").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/schedule/getAll/{busOperatorId}").hasAnyAuthority("PASSENGER", "ADMIN", "BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/route/getAll").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/route/searchRoutes").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/bus/delete/{busId}").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.POST, "/api/booking/process-booking/{bookingId}").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.POST, "/api/booking/cancelBooking/{requestId}").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/booking/bookedSeats/{scheduleId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/passenger/admin/getAllPassengers").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/passenger/admin/passengerById").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/user/passenger/userProfile").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.GET, "/api/user/busOperator/userProfile").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/booking/stat/bookings-by-month").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/route/soft-delete/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/tickets/get/{bookingId}").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.GET, "/api/route/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/cancel/cancel-request/{bookingId}").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.GET, "/api/route/top").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/passenger/id/upload/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/bus/types").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/operator/combined-stats").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/cancel/cancel-requests").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.POST, "/api/cancel/reject-request/{requestId}").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.GET, "/api/admin/combined-stat").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/passenger/update").hasAuthority("PASSENGER")
                .requestMatchers(HttpMethod.PUT, "/api/operator/update").hasAuthority("BUS_OPERATOR")
                .requestMatchers(HttpMethod.PUT, "/api/user/change-password").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/user/all").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/user/disable/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/user/enable/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/schedule/soft-delete/{id}").hasAuthority("BUS_OPERATOR")
                .anyRequest().authenticated());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder);
        return dao;
    }


}

