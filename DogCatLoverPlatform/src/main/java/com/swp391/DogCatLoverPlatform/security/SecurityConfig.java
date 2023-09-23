package com.swp391.DogCatLoverPlatform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//Class sẽ được web khi Spring boot chạy ở tầng config
@EnableWebSecurity//Custom Spring Security
public class SecurityConfig {

    // tạo ra chuẩn mã hóa password và lưu trữ ở IOC
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
        Tạo danh sách user ảo lưu trên thanh ram để chứng thực
    */

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();
        // Lưu trữ 2 user vừa tạo lưu trữ trên RAM
        return new InMemoryUserDetailsManager(admin,user);
    }


    //Cấu hình chứng thực các link chỉ định cho Security ******
    //GET /product ai truy cập cũng được
    //PUT /product ADMIN mới truy cập được
    //GET /cart USER mới truy cập được



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .antMatchers("/DogCatLoverPlatform/**").permitAll()
                .antMatchers("/DEMOJPA/**").permitAll()
                    .anyRequest().authenticated()
                .and().httpBasic()
                .and().build();
    }
}
