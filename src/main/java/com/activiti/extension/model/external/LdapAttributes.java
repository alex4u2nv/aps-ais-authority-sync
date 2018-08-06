package com.activiti.extension.model.external;

import java.util.Arrays;

/**
 * @author Alexander Mahabir
 */
public class LdapAttributes {

  private String[] LDAP_ENTRY_DN;
  private String[] LDAP_ID;
  private String[] createTimestamp;
  private String[] modifyTimestamp;

  @Override
  public String toString() {
    return "LdapAttributes{" +
        "LDAP_ENTRY_DN=" + Arrays.toString(LDAP_ENTRY_DN) +
        ", LDAP_ID=" + Arrays.toString(LDAP_ID) +
        ", createTimestamp=" + Arrays.toString(createTimestamp) +
        ", modifyTimestamp=" + Arrays.toString(modifyTimestamp) +
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
    LdapAttributes that = (LdapAttributes) o;
    return Arrays.equals(LDAP_ENTRY_DN, that.LDAP_ENTRY_DN) &&
        Arrays.equals(LDAP_ID, that.LDAP_ID) &&
        Arrays.equals(createTimestamp, that.createTimestamp) &&
        Arrays.equals(modifyTimestamp, that.modifyTimestamp);
  }

  @Override
  public int hashCode() {

    int result = Arrays.hashCode(LDAP_ENTRY_DN);
    result = 31 * result + Arrays.hashCode(LDAP_ID);
    result = 31 * result + Arrays.hashCode(createTimestamp);
    result = 31 * result + Arrays.hashCode(modifyTimestamp);
    return result;
  }

  public void setLDAP_ENTRY_DN(String[] LDAP_ENTRY_DN) {
    this.LDAP_ENTRY_DN = LDAP_ENTRY_DN;
  }

  public void setLDAP_ID(String[] LDAP_ID) {
    this.LDAP_ID = LDAP_ID;
  }

  public void setCreateTimestamp(String[] createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public void setModifyTimestamp(String[] modifyTimestamp) {
    this.modifyTimestamp = modifyTimestamp;
  }

  public LdapAttributes() {
  }

  private LdapAttributes(Builder builder) {
    createTimestamp = builder.createTimestamp;
    modifyTimestamp = builder.modifyTimestamp;
    LDAP_ENTRY_DN = builder.LDAP_ENTRY_DN;
    LDAP_ID = builder.LDAP_ID;
  }

  public String[] getLDAP_ENTRY_DN() {
    return LDAP_ENTRY_DN;
  }

  public String[] getLDAP_ID() {
    return LDAP_ID;
  }

  public String[] getCreateTimestamp() {
    return createTimestamp;
  }

  public String[] getModifyTimestamp() {
    return modifyTimestamp;
  }

  public static Builder builder() {
    return new Builder();
  }


  public static final class Builder {

    private String[] createTimestamp;
    private String[] modifyTimestamp;
    private String[] LDAP_ENTRY_DN;
    private String[] LDAP_ID;

    private Builder() {
    }

    public Builder withCreateTimestamp(String[] val) {
      createTimestamp = val;
      return this;
    }

    public Builder withLDAP_ID(String[] val) {
      LDAP_ID = val;
      return this;
    }

    public Builder withLDAP_ID(String val) {
      LDAP_ID = new String[]{val};
      return this;

    }

    public Builder withLDAP_ENTRY_DN(String[] val) {
      LDAP_ENTRY_DN = val;
      return this;
    }

    public Builder withLDAP_ENTRY_DN(String val) {
      LDAP_ENTRY_DN = new String[]{val};
      return this;
    }

    public Builder withCreateTimestamp(String val) {
      createTimestamp = new String[]{val};
      return this;
    }

    public Builder withModifyTimestamp(String val) {
      modifyTimestamp = new String[]{val};
      return this;
    }

    public Builder withModifyTimestamp(String[] val) {
      modifyTimestamp = val;
      return this;
    }

    public LdapAttributes build() {
      return new LdapAttributes(this);
    }
  }
}
