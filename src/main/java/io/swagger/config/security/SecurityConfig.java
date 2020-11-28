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

import static io.swagger.commons.Constant.ASTERISKS;
import static io.swagger.commons.Constant.FWD_SLASH;
import static io.swagger.commons.Constant.REQ_MAPPING_ASTERISKS;
import static io.swagger.commons.Constant.REQ_MAPPING_PEOPLE;
import static io.swagger.commons.Constant.ROLE_ADMIN;
import static io.swagger.commons.Constant.ROLE_SERVICE;
import static io.swagger.commons.Constant.SOUT_USERNAME_PASSWORD;

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
        UserDetails adminUser = User.withUsername(iVaultConfig.getAdminUsername()).password(encoder.encode(iVaultConfig.getAdminPassword())).roles(ROLE_SERVICE, ROLE_ADMIN).build();
        UserDetails serviceUser = User.withUsername(iVaultConfig.getServiceUsername()).password(encoder.encode(iVaultConfig.getServicePassword())).roles(ROLE_SERVICE).build();

        // remember the password that is printed out and use in the next step
        log.debug("Vault Admin-User Secrets");
        log.debug(SOUT_USERNAME_PASSWORD, iVaultConfig.getAdminUsername(), iVaultConfig.getAdminPassword());
        log.debug("Vault Service-User Secrets");
        log.debug(SOUT_USERNAME_PASSWORD, iVaultConfig.getServiceUsername(), iVaultConfig.getServicePassword());
        log.debug("Vault Database-User Secrets");
        log.debug(SOUT_USERNAME_PASSWORD, iVaultConfig.getDatabaseUsername(), iVaultConfig.getDatabasePassword());

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
                .antMatchers(HttpMethod.GET, REQ_MAPPING_PEOPLE).hasRole(ROLE_SERVICE)//
                .antMatchers(HttpMethod.POST, REQ_MAPPING_PEOPLE).hasRole(ROLE_SERVICE)//
                .antMatchers(HttpMethod.PUT, REQ_MAPPING_PEOPLE + REQ_MAPPING_ASTERISKS).hasRole(ROLE_SERVICE)//
                .antMatchers(HttpMethod.PATCH, REQ_MAPPING_PEOPLE + REQ_MAPPING_ASTERISKS).hasRole(ROLE_SERVICE)//
                .antMatchers(HttpMethod.DELETE, REQ_MAPPING_PEOPLE + REQ_MAPPING_ASTERISKS).hasRole(ROLE_ADMIN)
                .and()
                .antMatcher(ASTERISKS + FWD_SLASH + "h2-console" + REQ_MAPPING_ASTERISKS).authorizeRequests()
                .and()
                .formLogin()
                .loginPage(FWD_SLASH + "login").permitAll()
                .and()
                .logout().permitAll();
    }
}
