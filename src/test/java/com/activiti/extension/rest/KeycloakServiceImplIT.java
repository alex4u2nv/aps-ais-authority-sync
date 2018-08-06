package com.activiti.extension.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.activiti.extension.model.external.KeycloakGroup;
import com.activiti.extension.model.external.KeycloakToken;
import com.activiti.extension.model.external.KeycloakUser;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
 * @author Alexander Mahabir
 * This Integration test depends on Keycloak Running. Ensure that it si running via docker-compose
 *
 * docker-compose up or docker-compose start
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    KeycloakServiceImpl.class
})

@TestPropertySource("classpath:test.properties")
public class KeycloakServiceImplIT {


  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakServiceImplIT.class);
  @Autowired
  KeycloakService keycloakService;

  /**
   * Assert that we hav our keycloak service before moving forward with tests.
   * @throws Exception
   */
  @Before
  public void beforeTest() throws Exception {
    assertThat(keycloakService).isNotNull();
  }

  /**
   * Check that we can get an authentication token.
   * @throws Exception
   */
  @Test
  public void testAuthenticate() throws Exception {
    KeycloakToken token = keycloakService.connect();
    assertThat(token).isNotNull();
    LOGGER.trace("Token: {}", token);
  }

  /**
   * Validate that we're getting more than one user; keycloak generally has atleast 1 user: admin
   * @throws Exception
   */
  @Test
  public void testUserCount() throws Exception {
    Integer count = keycloakService.getTotalUsers();
    assertThat(count).isNotNull();
    assertThat(count).isGreaterThan(1);
    LOGGER.debug("Count: {}", count);
  }

  /**
   * Test that we can get page of users back from keycloak environment
   * @throws Exception
   */
  @Test
  public void testUserPage() throws Exception {
    final int MAX = 100;
    final int START = 0;
    Integer count = keycloakService.getTotalUsers();

    List<KeycloakUser> users = keycloakService.getUsers(START, MAX).get();
    assertThat(users).isNotNull();

    if (count > MAX) {
      assertThat(users.size()).isEqualTo(MAX);
    } else {
      assertThat(users.size()).isEqualTo(count);
    }

  }

  @Test
  public void testGetGroups() throws Exception {
    Optional<Set<KeycloakGroup>> groups = keycloakService.getGroups();
    assertThat(groups).isNotEmpty();
    LOGGER.debug("Groups {} ", groups.get());
  }


}
