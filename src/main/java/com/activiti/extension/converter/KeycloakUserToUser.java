package com.activiti.extension.converter;

import com.activiti.extension.model.external.KeycloakUser;
import com.activiti.extension.model.internal.ExternalUser;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Mahabir
 *
 * Convert API Model to Internally used model
 */
@Component
public class KeycloakUserToUser implements
    Converter<KeycloakUser, ExternalUser> {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUserToUser.class);

  @Autowired
  private KeycloakDateToDate keycloakDateToDate;

  public ExternalUser convert(KeycloakUser keycloakUser) {
    Date date = null;
    if (keycloakUser == null) {
      return null;
    }
    if (keycloakUser.getAttributes() != null) {
      date = keycloakDateToDate.convert(keycloakUser.getAttributes().getModifyTimestamp());
    }

    ExternalUser externalUser = ExternalUser.builder()
        .withEmail(keycloakUser.getEmail())
        .withFirstName(keycloakUser.getFirstName())
        .withId(keycloakUser.getUsername())
        .withLastName(keycloakUser.getLastName())
        .withLastModifiedTimeStamp(date)
        .withOriginalSrcId(keycloakUser.getId())
        .build();
    return externalUser;
  }

  public KeycloakUserToUser() {

  }

  public KeycloakUserToUser(KeycloakDateToDate keycloakDateToDate) {
    this.keycloakDateToDate = keycloakDateToDate;
  }
}
