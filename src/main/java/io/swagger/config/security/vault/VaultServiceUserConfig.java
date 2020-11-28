package io.swagger.config.security.vault;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("serv")
final class VaultServiceUserConfig {
    private String username;
    private String password;
}
