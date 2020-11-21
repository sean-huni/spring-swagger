package io.swagger.config.security.vault;

import io.swagger.config.security.IVaultConfig;
import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = {VaultAdminUserConfig.class, VaultDBUserConfig.class, VaultServiceUserConfig.class})
@Configuration
@Getter
public class VaultConfigImpl implements IVaultConfig {

    private final String adminUsername;
    private final String adminPassword;
    private final String serviceUsername;
    private final String servicePassword;
    private final String databaseUsername;
    private final String databasePassword;

    public VaultConfigImpl(VaultAdminUserConfig vaultAdminUserConfig, VaultDBUserConfig vaultDBUserConfig, VaultServiceUserConfig vaultServiceUserConfig) {
        adminUsername = vaultAdminUserConfig.getUsername();
        adminPassword = vaultAdminUserConfig.getPassword();
        serviceUsername = vaultServiceUserConfig.getUsername();
        servicePassword = vaultServiceUserConfig.getPassword();
        databaseUsername = vaultDBUserConfig.getUsername();
        databasePassword = vaultDBUserConfig.getPassword();
    }

}
