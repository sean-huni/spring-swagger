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

    private final IVaultConfig iVaultConfig;

    public SecurityConfig(IVaultConfig iVaultConfig) {
        this.iVaultConfig = iVaultConfig;
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails adminUser = User.withUsername(iVaultConfig.getAdminUsername()).password(encoder.encode(iVaultConfig.getAdminPassword())).roles("SERVICE", "ADMIN").build();
        UserDetails serviceUser = User.withUsername(iVaultConfig.getServiceUsername()).password(encoder.encode(iVaultConfig.getServicePassword())).roles("SERVICE").build();

        // remember the password that is printed out and use in the next step
        log.warn("Vault Admin-User Secrets");
        log.info("Username: {}, Password: {}", iVaultConfig.getAdminUsername(), iVaultConfig.getAdminPassword());
        log.warn("Vault Service-User Secrets");
        log.info("Username: {}, Password: {}", iVaultConfig.getServiceUsername(), iVaultConfig.getServicePassword());
        log.warn("Vault Database-User Secrets");
        log.info("Username: {}, Password: {}", iVaultConfig.getDatabaseUsername(), iVaultConfig.getDatabasePassword());

        return new InMemoryUserDetailsManager(adminUser, serviceUser);
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
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/people").hasRole("SERVICE")//
                .antMatchers(HttpMethod.POST, "/people").hasRole("SERVICE")//
                .antMatchers(HttpMethod.PUT, "/people/**").hasRole("SERVICE")//
                .antMatchers(HttpMethod.PATCH, "/people/**").hasRole("SERVICE")//
                .antMatchers(HttpMethod.DELETE, "/people/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .antMatcher("**/h2-console/**").authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}
