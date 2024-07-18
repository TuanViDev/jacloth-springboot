package com.vanlang.webbanquanao.Config;

import com.vanlang.webbanquanao.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.security.Provider;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * An example of explicitly configuring Spring Security with the defaults.
 *
 * @author Rob Winch
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/img/**", "/js/**", "/css/**", "/webjars/**","/api/guest/**").permitAll()
                        .requestMatchers("/cart","/invoice", "/checkout").authenticated()
                        .requestMatchers("/","/home","/register","/product/**").permitAll()
                        .requestMatchers("/admin/**","/api/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults())

                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/j_spring_security_check")
                        .defaultSuccessUrl("/?loginSuccess")
                        .failureUrl("/login?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true) // Hủy phiên làm việc.
                        .clearAuthentication(true) // Xóa xác thực.
                        .permitAll()
                )

//                .rememberMe(rememberMe -> rememberMe
//                                .key("vanlang")
//                                .rememberMeCookieName("vanlang")
//                                .tokenValiditySeconds(24 * 60 * 60)
//                .userDetailsService(userDetailsServiceAutoConfiguration())
// )
                 .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403"))

                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập.
                        .expiredUrl("/login")) // Trang khi phiên hết hạn.)

                .httpBasic(httpBasic -> httpBasic
                                .realmName("vanlang"))

        ;
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


}
