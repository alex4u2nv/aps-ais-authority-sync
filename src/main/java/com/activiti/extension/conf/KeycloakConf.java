package com.activiti.extension.conf;

import com.activiti.extension.converter.KeycloakDateToDate;
import com.activiti.extension.converter.KeycloakGroupToGroup;
import com.activiti.extension.converter.KeycloakUserToUser;
import com.activiti.extension.rest.KeycloakService;
import com.activiti.extension.rest.KeycloakServiceImpl;
import com.activiti.extension.services.KeycloakSyncService;
import com.activiti.extension.services.KeycloakSyncServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConf {


  @Bean
  public KeycloakService getKeycloakService() {
    return new KeycloakServiceImpl();
  }

  @Bean
  public KeycloakSyncService getKeycloakSyncService() {
    return new KeycloakSyncServiceImpl();
  }

  @Bean
  public KeycloakUserToUser getKeycloakUserToExternalUser() {
    return new KeycloakUserToUser();
  }

  @Bean
  public KeycloakDateToDate getKeycloakDateToDate() {
    return new KeycloakDateToDate();
  }

  @Bean
  public KeycloakGroupToGroup getKeycloakGroupToGroup() {
    return new KeycloakGroupToGroup();
  }

}
