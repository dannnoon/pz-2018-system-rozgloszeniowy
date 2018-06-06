package com.pz.broadcast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;

    private final Environment env;

    @Autowired
    public SecurityConfig(AccessDeniedHandler accessDeniedHandler, Environment env) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.env = env;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource()).passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT email, password, id FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("   SELECT u.email, r.name" +
                                            "   FROM user u" +
                                            "   INNER JOIN user_roles ur" +
                                            "   ON ur.users_id = u.id" +
                                            "   INNER JOIN role r" +
                                            "   ON ur.roles_id = r.id" +
                                            "   WHERE email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                .antMatchers("/js/**")
                .permitAll()
                .antMatchers("/404", "/403", "/auth/logout", "/auth/login", "/auth/isLogged")
                .permitAll()
                .antMatchers("/api/**", "/getRoles", "/auth/**")
                .hasAnyAuthority("USER", "GLOBAL_ADMINISTRATOR", "GROUP_ADMINISTRATOR")
                .antMatchers("/**")
                .permitAll()
                    .anyRequest()
                        .denyAll()
                    .and()
                .formLogin()
                .loginPage("/login") // call our view replace /login with whatever view name for login page
                .loginProcessingUrl("/auth/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login/error")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/register" );
    }

    private DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        driverManagerDataSource.setUrl(env.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }
}
