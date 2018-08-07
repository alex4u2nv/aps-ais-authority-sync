package com.activiti.extension.model.internal;

import com.activiti.domain.sync.ExternalIdmGroup;
import com.activiti.domain.sync.ExternalIdmQueryResult;
import com.activiti.domain.sync.ExternalIdmUser;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Alexander Mahaibr
 */
public class ExternalObjects implements ExternalIdmQueryResult {
  private final List<ExternalGroup> groups;
  private final List<ExternalUser> users;

  private ExternalObjects(Builder builder) {
    groups = builder.groups;
    users = builder.users;
  }

  public boolean hasGroups() {
    return Optional.ofNullable(groups).isPresent();
  }

  public boolean hasUsers() {
    return Optional.ofNullable(users).isPresent();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExternalObjects that = (ExternalObjects) o;
    return Objects.equals(groups, that.groups) &&
        Objects.equals(users, that.users);
  }

  @Override
  public String toString() {
    return "ExternalObjects{" +
        "groups=" + groups +
        ", users=" + users +
        '}';
  }

  @Override
  public int hashCode() {

    return Objects.hash(groups, users);
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public List<? extends ExternalIdmGroup> getGroups() {
    return groups;
  }

  @Override
  public List<? extends ExternalIdmUser> getUsers() {
    return users;
  }


  public static final class Builder {

    private List<ExternalGroup> groups;
    private List<ExternalUser> users;

    private Builder() {
    }

    public Builder withGroups(List<ExternalGroup> val) {
      groups = val;
      return this;
    }

    public Builder withUsers(List<ExternalUser> val) {
      users = val;
      return this;
    }

    public ExternalObjects build() {
      return new ExternalObjects(this);
    }
  }
}
