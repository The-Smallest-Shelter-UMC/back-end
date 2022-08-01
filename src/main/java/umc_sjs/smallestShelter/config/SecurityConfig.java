/*
package umc_sjs.smallestShelter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import umc_sjs.smallestShelter.config.jwt.JwtAuthenticationFilter;
import umc_sjs.smallestShelter.config.jwt.JwtAuthorizationFilter;
import umc_sjs.smallestShelter.repository.JoinDtoRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JoinDtoRepository joinDtoRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 사용X
                .and()
                .addFilter(corsFilter)  // cors 정책에서 벗어나기 (@CrossOrigin 하고 다름)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))  // AuthenticationManager
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), joinDtoRepository))
                .authorizeRequests()
                .antMatchers("/user/**", "/auth/**")
                    .access("hasRole('PRIVATE')")
                .antMatchers("/animal/**", "/post/**")
                .access("hasRole('ORGANIZATION')")
                .anyRequest().permitAll();
    }
}
*/
