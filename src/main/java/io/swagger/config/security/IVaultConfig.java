package io.swagger.config.security;

public interface IVaultConfig {

    String getAdminUsername();

    String getAdminPassword();

    String getServiceUsername();

    String getServicePassword();

    String getDatabaseUsername();

    String getDatabasePassword();
}
