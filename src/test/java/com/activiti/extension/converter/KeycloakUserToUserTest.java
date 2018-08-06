package com.activiti.extension.converter;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.activiti.domain.sync.ExternalIdmUser;
import com.activiti.extension.model.external.KeycloakUser;
import com.activiti.extension.model.external.LdapAccess;
import com.activiti.extension.model.external.LdapAttributes;
import com.activiti.extension.model.internal.ExternalUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alexander Mahabir
 */
@RunWith(MockitoJUnitRunner.class)
public class KeycloakUserToUserTest {

  private KeycloakUserToUser keycloakUserToUser;

  @InjectMocks
  private KeycloakDateToDate keycloakDateToDate;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
    keycloakUserToUser = new KeycloakUserToUser(keycloakDateToDate);
  }

  /**
   * Test the conversion of a null user
   * @throws Exception
   */
  @Test
  public void testConversionNullCheck() throws Exception {
    ExternalIdmUser user = keycloakUserToUser.convert(null);
    assertThat(user).isNull();
  }

  /**
   * Test the conversion of Keycloak user to an external idm user
   * @throws Exception
   */
  @Test
  public void testConversion() throws Exception {
    final KeycloakUser keycloakUser = KeycloakUser.builder()
        .withAccess(LdapAccess.builder()
            .withImpersonate(true)
            .withManage(true)
            .withManageGroupMembership(false)
            .withMapRoles(false)
            .withView(false)
            .build())
        .withAttributes(LdapAttributes.builder()
            .withCreateTimestamp("20180802144725Z")
            .withModifyTimestamp("20180802144725Z")
            .withLDAP_ENTRY_DN("cn=Alexander Mahabir,ou=Consulting,dc=alfresco,dc=com")
            .withLDAP_ID("b7423970-2aae-1038-9ca5-717ca1562e0")
            .build())
        .withCreatedTimestamp(1533221252500l)
        .withEmail("alex.mahabir@alfresco.com")
        .withEmailVerified(true)
        .withEnabled(true)
        .withFirstName("Alexander")
        .withLastName("Mahabir")
        .withUsername("amahabir")
        .withId("1f00672a-5980-42a0-95fd-5b82709de67a")
        .build();

    assertThat(keycloakUser).isNotNull();

    ExternalUser externalUser = keycloakUserToUser.convert(keycloakUser);

    assertThat(externalUser).isNotNull();
    assertThat(externalUser.getEmail()).isEqualTo(keycloakUser.getEmail());
    assertThat(externalUser.getFirstName()).isEqualTo(keycloakUser.getFirstName());
    assertThat(externalUser.getLastName()).isEqualTo(keycloakUser.getLastName());
    assertThat(externalUser.getId()).isEqualTo(keycloakUser.getUsername());
    assertThat(externalUser.getLastModifiedTimeStamp())
        .isEqualTo(keycloakDateToDate.convert(keycloakUser.getAttributes().getModifyTimestamp()));
  }
}
