package com.swp391.DogCatLoverPlatform.config;

import com.swp391.DogCatLoverPlatform.filter.JwtFilter;
import com.swp391.DogCatLoverPlatform.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Class sẽ được quét khi spring boot chạy ở thầng config
@EnableWebSecurity //custom spring security
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    CustomAuthenProvider customAuthenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                    .antMatchers("/css/**", "/js/**", "/images/**", "/webfonts/**", "/fonts/**", "/video/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/index/**").permitAll()
                    .antMatchers("/service/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/index/login").permitAll()
                    .antMatchers(HttpMethod.POST,"/index/**").permitAll()
                    .antMatchers("/cdn-cgi/**").permitAll()
                    .antMatchers("/blog/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/blog/**").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                    .loginPage("/index/home") // Trang đăng nhập của bạn (nếu có)
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll()
//                .and()
                .build();
    }

}
