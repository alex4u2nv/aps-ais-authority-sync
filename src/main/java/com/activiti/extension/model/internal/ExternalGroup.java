package com.activiti.extension.model.internal;

import com.activiti.domain.sync.ExternalIdmGroup;
import com.activiti.domain.sync.ExternalIdmGroupImpl;
import com.activiti.domain.sync.ExternalIdmUser;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Mahabir
 */
public class ExternalGroup extends ExternalIdmGroupImpl {

  private ExternalGroup(Builder builder) {
    setName(builder.name);
    setOriginalSrcId(builder.originalSrcId);
    setLastModifiedTimeStamp(builder.lastModifiedTimeStamp);
    setUsers(builder.users);
    setChildGroups(builder.childGroups);
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return "ExternalGroup{" +
        "name='" + name + '\'' +
        ", originalSrcId='" + originalSrcId + '\'' +
        ", lastModifiedTimeStamp=" + lastModifiedTimeStamp +
        ", users=" + users +
        ", childGroups=" + childGroups +
        '}';
  }

  public boolean hasChildGroups() {
    return Optional.ofNullable(childGroups).isPresent();
  }
  public static final class Builder {

    private String name;
    private String originalSrcId;
    private Date lastModifiedTimeStamp;
    private List<? extends ExternalIdmUser> users;
    private List<? extends ExternalIdmGroup> childGroups;

    private Builder() {
    }

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withOriginalSrcId(String val) {
      originalSrcId = val;
      return this;
    }

    public Builder withLastModifiedTimeStamp(Date val) {
      lastModifiedTimeStamp = val;
      return this;
    }

    public Builder withUsers(List<? extends ExternalIdmUser> val) {
      users = val;
      return this;
    }

    public Builder withChildGroups(List<? extends ExternalIdmGroup> val) {
      childGroups = val;
      return this;
    }

    public ExternalGroup build() {
      return new ExternalGroup(this);
    }
  }
}
