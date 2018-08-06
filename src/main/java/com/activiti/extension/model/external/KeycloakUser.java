package com.activiti.extension.model.external;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Alexander Mahabir
 *
 * This is a representation of the Keycloak's User Object that is returned
 * by the keycloak's User API
 */
public class KeycloakUser {

  private String id;
  private Long createdTimestamp;
  private String username;
  private boolean enabled;
  private boolean totp;
  private boolean emailVerified;
  private String firstName;
  private String lastName;
  private String email;
  private String federationLink;
  private String[] disableableCredentialTypes;
  private String[] requiredActions;
  private Long notBefore;
  private LdapAttributes attributes;
  private LdapAccess access;

  public KeycloakUser() {
  }

  @Override
  public String toString() {
    return "KeycloakUser{" +
        "id='" + id + '\'' +
        ", createdTimestamp=" + createdTimestamp +
        ", username='" + username + '\'' +
        ", enabled=" + enabled +
        ", totp=" + totp +
        ", emailVerified=" + emailVerified +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", federationLink='" + federationLink + '\'' +
        ", disableableCredentialTypes=" + Arrays.toString(disableableCredentialTypes) +
        ", requiredActions=" + Arrays.toString(requiredActions) +
        ", notBefore=" + notBefore +
        ", attributes=" + attributes +
        ", access=" + access +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeycloakUser that = (KeycloakUser) o;
    return enabled == that.enabled &&
        totp == that.totp &&
        emailVerified == that.emailVerified &&
        Objects.equals(id, that.id) &&
        Objects.equals(createdTimestamp, that.createdTimestamp) &&
        Objects.equals(username, that.username) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(email, that.email) &&
        Objects.equals(federationLink, that.federationLink) &&
        Arrays.equals(disableableCredentialTypes, that.disableableCredentialTypes) &&
        Arrays.equals(requiredActions, that.requiredActions) &&
        Objects.equals(notBefore, that.notBefore) &&
        Objects.equals(attributes, that.attributes) &&
        Objects.equals(access, that.access);
  }

  @Override
  public int hashCode() {

    int result = Objects
        .hash(id, createdTimestamp, username, enabled, totp, emailVerified, firstName, lastName,
            email, federationLink, notBefore, attributes, access);
    result = 31 * result + Arrays.hashCode(disableableCredentialTypes);
    result = 31 * result + Arrays.hashCode(requiredActions);
    return result;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCreatedTimestamp(Long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setTotp(boolean totp) {
    this.totp = totp;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFederationLink(String federationLink) {
    this.federationLink = federationLink;
  }

  public void setDisableableCredentialTypes(String[] disableableCredentialTypes) {
    this.disableableCredentialTypes = disableableCredentialTypes;
  }

  public void setRequiredActions(String[] requiredActions) {
    this.requiredActions = requiredActions;
  }

  public void setNotBefore(Long notBefore) {
    this.notBefore = notBefore;
  }

  public void setAttributes(LdapAttributes attributes) {
    this.attributes = attributes;
  }

  public void setAccess(LdapAccess access) {
    this.access = access;
  }

  private KeycloakUser(Builder builder) {
    id = builder.id;
    createdTimestamp = builder.createdTimestamp;
    username = builder.username;
    enabled = builder.enabled;
    totp = builder.totp;
    emailVerified = builder.emailVerified;
    firstName = builder.firstName;
    lastName = builder.lastName;
    email = builder.email;
    federationLink = builder.federationLink;
    disableableCredentialTypes = builder.disableableCredentialTypes;
    requiredActions = builder.requiredActions;
    notBefore = builder.notBefore;
    attributes = builder.attributes;
    access = builder.access;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getId() {
    return id;
  }

  public Long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public String getUsername() {
    return username;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isTotp() {
    return totp;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getFederationLink() {
    return federationLink;
  }

  public String[] getDisableableCredentialTypes() {
    return disableableCredentialTypes;
  }

  public String[] getRequiredActions() {
    return requiredActions;
  }

  public Long getNotBefore() {
    return notBefore;
  }

  public LdapAttributes getAttributes() {
    return attributes;
  }

  public LdapAccess getAccess() {
    return access;
  }


  public static final class Builder {

    private String id;
    private Long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private String federationLink;
    private String[] disableableCredentialTypes;
    private String[] requiredActions;
    private Long notBefore;
    private LdapAttributes attributes;
    private LdapAccess access;

    private Builder() {
    }

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withCreatedTimestamp(Long val) {
      createdTimestamp = val;
      return this;
    }

    public Builder withUsername(String val) {
      username = val;
      return this;
    }

    public Builder withEnabled(boolean val) {
      enabled = val;
      return this;
    }

    public Builder withTotp(boolean val) {
      totp = val;
      return this;
    }

    public Builder withEmailVerified(boolean val) {
      emailVerified = val;
      return this;
    }

    public Builder withFirstName(String val) {
      firstName = val;
      return this;
    }

    public Builder withLastName(String val) {
      lastName = val;
      return this;
    }

    public Builder withEmail(String val) {
      email = val;
      return this;
    }

    public Builder withFederationLink(String val) {
      federationLink = val;
      return this;
    }

    public Builder withDisableableCredentialTypes(String[] val) {
      disableableCredentialTypes = val;
      return this;
    }

    public Builder withRequiredActions(String[] val) {
      requiredActions = val;
      return this;
    }

    public Builder withNotBefore(Long val) {
      notBefore = val;
      return this;
    }

    public Builder withAttributes(LdapAttributes val) {
      attributes = val;
      return this;
    }

    public Builder withAccess(LdapAccess val) {
      access = val;
      return this;
    }

    public KeycloakUser build() {
      return new KeycloakUser(this);
    }
  }
}
