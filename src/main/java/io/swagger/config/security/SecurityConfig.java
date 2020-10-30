package io.swagger.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Log4j2
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String adminUsername = "admin";
    private static final String adminPassword = "admin_password";
    private static final String serviceUsername = "service";
    private static final String servicePassword = "service_password";

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails adminUser = User.withUsername(adminUsername).password(encoder.encode(adminPassword)).roles("SERVICE", "ADMIN").build();
        UserDetails demoUser = User.withUsername(serviceUsername).password(encoder.encode(servicePassword)).roles("SERVICE").build();

        // remember the password that is printed out and use in the next step
        log.info("Username: {}, Password: {}", adminUsername, adminPassword);
        log.info("Username: {}, Password: {}", serviceUsername, servicePassword);

        return new InMemoryUserDetailsManager(adminUser, demoUser);
    }

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     * <p>
     * Any endpoint that requires defense against common vulnerabilities can be specified
     * here, including public ones. See {@link HttpSecurity#authorizeRequests} and the
     * `permitAll()` authorization rule for more details on public endpoints.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and()
                .authorizeRequests().//
                antMatchers(HttpMethod.POST, "/people").hasRole("ADMIN").//
                antMatchers(HttpMethod.PUT, "/people/**").hasRole("SERVICE").//
                antMatchers(HttpMethod.PATCH, "/people/**").hasRole("SERVICE").//
                antMatchers(HttpMethod.DELETE, "/people/**").hasRole("ADMIN").and()
                .csrf().disable()
                .antMatcher("**/h2-console/**").authorizeRequests().anyRequest().permitAll();
    }
}
