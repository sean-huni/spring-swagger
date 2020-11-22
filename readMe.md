[![CircleCI](https://circleci.com/gh/sean-huni/spring-swagger/tree/dev.svg?style=svg)](https://circleci.com/gh/sean-huni/spring-swagger/tree/dev)   [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sean-huni_spring-swagger&branch=master&metric=alert_status)](https://sonarcloud.io/dashboard?branch=master&id=sean-huni_spring-swagger)
# Swagger Spring Boot Project

In light of some of the challenges experienced in most Java EE projects, this project have been built to express some 
formalities in the structure & manner in which restful APi's should be delivered to other teams. 

### Questions we aim to address
What are the standard CRUD(Create, Read, Update, Delete) operations of developing a Restful API?
How do we present it formally using HTTP Methods?
- HTTP.POST -> To create a new resource.
- HTTP.GET -> To retrieve an existing resource.
- HTTP.PUT -> To update an entire resource.
- HTTP.PATCH -> To perform a partial update to an existing resource.
- HTTP.DELETE -> delete an existing resource.

1. ##### How can we use Spring Data Rest to create operations that enable the above?
2. ##### How can we secure(with Spring Security) our restful API & protect it from unauthorised access?
3. ##### How can we enforce validations to the incoming request-payload to avoid incomplete/corrupt data to be saved in the Database?
4. ##### How do we adopt a TDD (Test Driven Development) approach to write good quality tests?
5. ##### How do we write tests for Spring Data Rest, Spring Security, Defensive Tests, Positive Scenarios Tests?

## Externalising Secrets with Vault
For the application to execute successfully it requires Vault. Vault Securely externalises the application's secretes.

### Vault Dev-Env
Launch the Vault Server in Dev mode. (NOTE: DO NOT DO THIS IN LIVE/PROD mode)

There are 2 ways to launch Vault:
1. Directly on your localhost machine on the localhost.
2. Launch as a docker instance.

#### 1. Launch via Localhost - Vault Installation (Linux 64-bit)
 ▶ `curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -`
 
 ▶ `sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"`
 
 ▶ `sudo apt update -y && sudo apt install -y vault`
 
 Verify that vault is stall successfully. It should display version info.
 
 ▶ `vault -version`
 
 Spin up the vault server
  
 ▶ `vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"`
 
 Open the browser `http://127.0.0.1:8200/ui/vault/secrets` to ensure that it's up & running.
 
 Open a new tab in your Terminal/Console & export the required the Environment-Variables
 
 ▶ `export VAULT_TOKEN=00000000-0000-0000-0000-000000000000`
 
 ▶ `export VAULT_ADDR=http://127.0.0.1:8200`

 Add/Put the secrets(key-value pair) into the vault

 ▶ `vault kv put secret/party-identity/dev admin.username=admin-user admin.password=Mut4nt-Squ@ serv.username=service-user serv.password=Imm0Rt4L~P@ss spring.datasource.username=dev-db spring.datasource.password=S3cret\*\*DB`
 
 Before launching the SpringBoot app using the 'live' profile, ensure that the live secrets are available in the vault. 
 
 ▶ `vault kv put secret/party-identity/live admin.username=admin-live admin.password=Mut4nt-l1ve-Squ@ serv.username=service-live serv.password=Imm0Rt4L~L1v3~P@ss spring.datasource.username=dev-db spring.datasource.password=l1v3_S3cret\*\*DB`

 PS: Take note of the escaped `**` on the DB-Password.
 
#### Launch as a Docker Instance
##### Set Env-Variable (Crucial)
If you're using an IDE (eg. IntelliJ IDEA) make sure you set your environment-variables in the Run-Configurations >> Edit-Configurations >> Environment-Variables.
 * `VAULT_TOKEN=00000000-0000-0000-0000-000000000000`
 * `VAULT_ADDR=http://127.0.0.1:8200`
 
Pull the Vault docker image

 ▶ `docker pull vault`
 
 Spin up the docker-container.
 
 ▶ `docker run --cap-add=IPC_LOCK -e 'VAULT_ADDR=http://127.0.0.1:8200' -e 'VAULT_DEV_ROOT_TOKEN_ID=00000000-0000-0000-0000-000000000000' -p 8200:8200 -v {hostPath}:/vault/logs --name dev-vault vault`
  
  PS: Make sure to replace `{hostPath}` with a local directory path, such as `/tmp/vault` on the host machine.
  
  `-d` to detach the console.
  
  shortcut to export dev  & live secrets
  
 ▶ `docker exec -e 'VAULT_TOKEN=00000000-0000-0000-0000-000000000000' -e 'VAULT_ADDR=http://127.0.0.1:8200' dev-vault sh -c "vault kv put secret/party-identity/dev admin.username=admin-user admin.password=Mut4nt-Squ@ serv.username=service-user serv.password=Imm0Rt4L~P@ss spring.datasource.username=dev-db spring.datasource.password=S3cret\*\*DB" `
 
 ▶ `docker exec -e 'VAULT_TOKEN=00000000-0000-0000-0000-000000000000' -e 'VAULT_ADDR=http://127.0.0.1:8200' dev-vault sh -c "vault kv put secret/party-identity/live admin.username=admin-live admin.password=Mut4nt-l1ve-Squ@ serv.username=service-live serv.password=Imm0Rt4L~L1v3~P@ss spring.datasource.username=dev-db spring.datasource.password=l1v3_S3cret\*\*DB" `
  
  Execute into the container
  
 ▶ `docker exec -e 'VAULT_TOKEN=00000000-0000-0000-0000-000000000000' -e 'VAULT_ADDR=http://127.0.0.1:8200' -it vault sh`
 
 Export the env-variables (within the vault docker-container).
 
 ▶ `export VAULT_TOKEN=00000000-0000-0000-0000-000000000000`
 
 ▶ `export VAULT_ADDR=http://127.0.0.1:8200`
 
 Check the status of the vault (within the vault docker-container). 
 
 ▶ `vault status`
 
 Activate vault logs
 
 ▶ `vault audit enable file file_path=/vault/logs/vault_audit.log`
 
 Add the Live-Profile Secrets
 
 ▶ `vault kv put secret/party-identity/live admin.username=admin-live admin.password=Mut4nt-l1ve-Squ@ serv.username=service-live serv.password=Imm0Rt4L~L1v3~P@ss spring.datasource.username=dev-db spring.datasource.password=l1v3_S3cret\*\*DB`
 
 Create a new policy for the token to be used.
 
    path "secret/party-identity" {
      capabilities = [ "read" ]
    }
    
    path "secret/party-identity,live" {
      capabilities = [ "read" ]
    }
    
    path "secret/party-identity,dev" {
      capabilities = [ "read" ]
    }
    
    path "secret/application" {
      capabilities = [ "read" ]
    }
    
    path "secret/application/*" {
      capabilities = [ "read" ]
    }
    
 Create the new token for the New-Policy
 
 ▶ `vault token create -policy=party-identity-policy`  
 
 Copy the token & seal the vault
 
  ▶ `vault operator seal`
  
  To unseal Vault, run:
  
  ▶ `vault operator unseal {unsealKey}`

Accepted vault secret creation usages:
 
     vault kv put secret/{application}/{profile}
     vault kv put secret/{application}
     vault kv put secret/{defaultContext}/{profile}
     vault kv put secret/{defaultContext}
 
### Vault Config - bootstrap.yml
     spring:
       application:
         name: party-identity
       profiles:
         active: dev
       cloud:
         vault:
           token: ${VAULT_TOKEN}
           scheme: http
           kv:
             enabled: true
           host: 127.0.0.1
           port: 8200
           authentication: TOKEN
         
## Launching The Project
There are various ways of launching the project. Either from an IDE(Integrated Development Environment) or from the Terminal.

### IntelliJ IDE
Once the project has been recognized as a Spring Boot Java Project navigate to the following class path:
 src >> java >> io >> swagger >> SpringSwaggerApplication.java

Right click & run the SpringSwaggerApplication.java

### Unix Terminal
Current working directory: Project root.
Execute the following to launch the project:

 - On Unix/MacOS
 
 ▶ `./gradlew bootRun`
    
 - On Windows:

 ▶ `gradlew.bat run`
    
Ctrl+C to terminate the application in execution.

### Vault Script
The author has written an additional script `salt` for preparing the environment before executing the tests.
Permitted arguments:

 ▶ `sh salt start` Starts/restarts/respawns the vault docker instance with the default credentials. 
 
 ▶ `sh salt stop` Stops & releases the vault docker instance from using up resources when not needed. 

### Next Up!!! The Browser
* Url where the local-app is running: [http://localhost:8082/customer-service](http://localhost:8082/customer-service)
 
### Executing the Tests
     
* On Unix/MacOS:
    
 ▶ `sh gradlew test`
       
* On Windows: 
    
 ▶ `gradlew.bat test`
 
# Restful API Swagger-Documentation
Once the application has been launched successfully, it's now time to play with it.
* [API Reference Documentation](http://localhost:8082/customer-service)
Click on Person's Entity to Expand the operations of th API.

### Additional Links & References
* [Best Practices for Restful API Design](https://avaldes.com/best-practices-for-restful-api-design/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Data Security Examples](https://github.com/spring-projects/spring-data-examples)
* [Quick Start Guide - Vault Dev Server](https://learn.hashicorp.com/tutorials/vault/getting-started-dev-server?in=vault/getting-started)
* [Spring Cloud Vault Guide](https://cloud.spring.io/spring-cloud-vault/1.0.2.RELEASE/)
* [Spring AOP](https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html)
* [Spring Vault Config](https://spring.io/guides/gs/vault-config/)
* [Docker Vault](https://github.com/hashicorp/docker-vault)

# Future Work
Part of making developing our Restful API. It is critical our API is versioned correctly for future changes & releases.
What are the Best Practices of Versioning our Restful API?
* [Thread on REST API Versioning with Spring](https://stackoverflow.com/questions/20198275/how-to-manage-rest-api-versioning-with-spring)

# About the Author
* [Sean's Resume](https://sean-huni.xyz)
