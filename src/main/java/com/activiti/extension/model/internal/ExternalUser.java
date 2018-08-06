package com.activiti.extension.model.internal;

import com.activiti.domain.sync.ExternalIdmUserImpl;
import java.util.Date;

/**
 * @author Alexander Mahabir
 * Key Cloak User Model that is used by APS External Identity Management Hook
 */
public class ExternalUser extends ExternalIdmUserImpl{

  private ExternalUser(Builder builder) {
    setOriginalSrcId(builder.originalSrcId);
    setId(builder.id);
    setEmail(builder.email);
    setFirstName(builder.firstName);
    setLastName(builder.lastName);
    setPassword(builder.password);
    setLastModifiedTimeStamp(builder.lastModifiedTimeStamp);
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "ExternalUser{" +
        "originalSrcId='" + originalSrcId + '\'' +
        ", id='" + id + '\'' +
        ", email='" + email + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        ", lastModifiedTimeStamp=" + lastModifiedTimeStamp +
        '}';
  }


  public static final class Builder {

    private String originalSrcId;
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Date lastModifiedTimeStamp;

    public Builder() {
    }

    public Builder withOriginalSrcId(String val) {
      originalSrcId = val;
      return this;
    }

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withEmail(String val) {
      email = val;
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

    public Builder withPassword(String val) {
      password = val;
      return this;
    }

    public Builder withLastModifiedTimeStamp(Date val) {
      lastModifiedTimeStamp = val;
      return this;
    }

    public ExternalUser build() {
      return new ExternalUser(this);
    }
  }
}
