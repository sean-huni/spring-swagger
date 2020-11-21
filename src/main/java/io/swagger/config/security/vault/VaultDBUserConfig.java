package io.swagger.config.security.vault;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.datasource")
final class VaultDBUserConfig {
    private String username;
    private String password;
}
