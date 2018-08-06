package com.activiti.extension.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.activiti.extension.converter.KeycloakDateToDate;
import com.activiti.extension.converter.KeycloakGroupToGroup;
import com.activiti.extension.converter.KeycloakUserToUser;
import com.activiti.extension.model.internal.ExternalObjects;
import com.activiti.extension.model.internal.ExternalUser;
import com.activiti.extension.rest.KeycloakService;
import com.activiti.extension.rest.KeycloakServiceImpl;
import com.activiti.extension.services.processors.ProcessByLastModified;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Alexander Mahabir This Integration test depends on Keycloak Running. Ensure that it si
 * running via docker-compose
 *
 * docker-compose up or docker-compose start
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    KeycloakServiceImpl.class,
    KeycloakSyncServiceImpl.class,
    KeycloakUserToUser.class,
    KeycloakDateToDate.class,
    KeycloakGroupToGroup.class
})

@TestPropertySource("classpath:test.properties")
public class KeycloakSyncServiceIT {


  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakSyncServiceIT.class);
  @Autowired
  KeycloakService keycloakService;

  @Autowired
  KeycloakSyncService keycloakSyncService;

  @Before
  public void beforeTest() throws Exception {
    assertThat(keycloakService).isNotNull();
  }

  private int count = 0;
  private final static int BATCH_SIZE = 100;

  private Gson gson;
  public KeycloakSyncServiceIT() {
    gson = new GsonBuilder().setPrettyPrinting().create();
  }
  /**
   * Test services that connect to keycloak and attempt to process all users.
   */
  @Test
  public void testGetAllUsers() throws Exception {
    Integer count = keycloakSyncService.getTotalUsers();
    assertThat(count).isGreaterThan(0);
    keycloakSyncService.processAllUsers((user) -> doNothing(user), BATCH_SIZE);
    LOGGER.debug("Visited {} items", this.count);
  }

  /**
   * @// TODO: 8/5/18 Adjust this to your integration enviroment No asertions in this test, as it's
   * realtive to when data was setup in keycloak etc. So adjust according to test case
   */
  @Test
  public void testProcessByLastModified() throws Exception {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss z", Locale.ENGLISH);
    final Date gmtDate = sdf.parse("2018080522725 GMT");
    LOGGER.debug("date: {}", gmtDate);
    ProcessByLastModified processByLastModified = new ProcessByLastModified(gmtDate);

    keycloakSyncService.processAllUsers(processByLastModified, BATCH_SIZE);
    assertThat(processByLastModified.getUsers()).isNotNull();
    LOGGER.debug("Processed {} users", processByLastModified.getUsers().size());
    LOGGER.debug("Users processed {}", processByLastModified.getUsers());

  }

  /**
   * Simple visitor method
   */
  public boolean doNothing(ExternalUser externalUser) {
    LOGGER.debug("{}",externalUser);
    this.count++;
    return true;
  }


  @Test
  public void testUserGroups() throws Exception {
    ExternalObjects externalObjects = keycloakSyncService.getAllUsersAndGroups();
    LOGGER.debug("Users and Groups");
    LOGGER.debug("{}", externalObjects);
  }


}
