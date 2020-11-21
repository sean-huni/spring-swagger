package io.swagger.config.security.vault;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("admin")
final class VaultAdminUserConfig {
    private String username;
    private String password;
}
