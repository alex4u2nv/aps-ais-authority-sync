package com.activiti.extension.bean;


import com.activiti.api.idm.AbstractExternalIdmSourceSyncService;
import com.activiti.domain.sync.ExternalIdmGroup;
import com.activiti.domain.sync.ExternalIdmQueryResult;
import com.activiti.domain.sync.ExternalIdmUser;
import com.activiti.extension.services.KeycloakSyncService;
import com.activiti.extension.services.processors.ProcessByLastModified;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Mahabir
 */
@Component
public class KeyCloakSync extends AbstractExternalIdmSourceSyncService {

  public static final String IDM_TYPE = "keycloak";
  private static final Logger LOGGER = LoggerFactory.getLogger(KeyCloakSync.class);

  @Value("${kc.diff.sync.cron:0 0/1 0 ? * *}")
  private String diffSyncCron;

  @Value("${kc.full.sync.cron:0 0/1 0 ? * *}")
  private String fullSyncCron;

  @Value("${kc.tenant.admins:admin@app.activiti.com}")
  private String[] tenantAdmins;

  @Value("${kc.tenant.managers:admin@app.activiti.com}")
  private String[] tenantManager;

  @Value("${kc.diff.sync.enabled:false}")
  private boolean diffSyncEnabled;

  @Value("${kc.full.sync.enabled:true}")
  private boolean fullSyncEnabled;

  @Value("${kc.sync.batch.size:100}")
  private int batchSize;

  @Autowired
  private KeycloakSyncService keycloakSyncService;


  protected String getIdmType() {
    LOGGER.debug("Returning IDM Type: {}", IDM_TYPE);
    return IDM_TYPE;
  }

  protected boolean isFullSyncEnabled(Long aLong) {
    LOGGER.debug("Full Sync Enabled: {}", fullSyncEnabled);
    return fullSyncEnabled;
  }

  protected boolean isDifferentialSyncEnabled(Long aLong) {
    LOGGER.debug("Differential Sync Enabled: {}", diffSyncEnabled);
    LOGGER.warn(
        " Keycloak's API doesn't support query by modified timestamp, so we'll be attempting it locally");
    return diffSyncEnabled;
  }

  /**
   * Keycloak doesn't synchronize groups from ldap; the only groups we can query, are groups that
   * are created and managed inside of keycloak.
   */
  protected ExternalIdmQueryResult getAllUsersAndGroupsWithResolvedMembers(Long aLong) {
    LOGGER.debug("Getting users and group");

    return keycloakSyncService.getAllUsersAndGroups();
  }

  /**
   *
   * @param date
   * @param aLong
   * @return
   */
  protected List<? extends ExternalIdmUser> getUsersModifiedSince(Date date, Long aLong) {
    LOGGER.debug("getting modified users {} {}", date, aLong);

    ProcessByLastModified userProcessor = new ProcessByLastModified(date);
    keycloakSyncService.processAllUsers(userProcessor, batchSize);

    LOGGER.debug("Collected {} users since {} for tenant id {}", userProcessor.getUsers().size(),
        date, aLong);

    return userProcessor.getUsers();
  }

  /**
   * Keycloak doesn't synchronize groups from ldap; so we'll postpone group sync for now.
   */
  protected List<? extends ExternalIdmGroup> getGroupsModifiedSince(Date date, Long aLong) {
    LOGGER.debug("getting modified since");
    return new ArrayList<>(keycloakSyncService.getAllGroupAndMembers());
  }

  protected String[] getTenantManagerIdentifiers(Long aLong) {
    LOGGER.debug("getting tenenant manager");

    return tenantManager;
  }

  protected String[] getTenantAdminIdentifiers(Long aLong) {

    return tenantAdmins;
  }

  protected String getScheduledFullSyncCronExpression() {
    return this.fullSyncCron;
  }

  protected String getScheduledDifferentialSyncCronExpression() {
    return diffSyncCron;
  }

  protected void additionalPostConstruct() {

  }

  public String getDiffSyncCron() {
    return diffSyncCron;
  }

  public void setDiffSyncCron(String diffSyncCron) {
    this.diffSyncCron = diffSyncCron;
  }

  public String getFullSyncCron() {
    return fullSyncCron;
  }

  public void setFullSyncCron(String fullSyncCron) {
    this.fullSyncCron = fullSyncCron;
  }


}
