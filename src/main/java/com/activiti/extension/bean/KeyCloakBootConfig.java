package com.activiti.extension.bean;

import com.activiti.api.boot.BootstrapConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Mahabir
 */
@Component
public class KeyCloakBootConfig implements BootstrapConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyCloakBootConfig.class);

  @Autowired
  private KeyCloakSync keyCloakSync;

  @Value("${kc.syncOnStartup:false}")
  private boolean syncOnStartup;

  public void applicationContextInitialized(ApplicationContext applicationContext) {
    if (syncOnStartup) {
      LOGGER.info("Executing Keycloak Full Sync");
      keyCloakSync.asyncExecuteFullSynchronization(null);
    } else {
      LOGGER.info("Keycloak Sync on startup is disabled");
    }
  }

  public void setSyncOnStartup(boolean syncOnStartup) {
    this.syncOnStartup = syncOnStartup;
  }

  public boolean isSyncOnStartup() {
    return syncOnStartup;
  }
}
