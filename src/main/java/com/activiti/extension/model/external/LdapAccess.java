package com.activiti.extension.model.external;

import java.util.Objects;

/**
 * @author Alexander Mahabir
 *
 * Based on KeyCloak's REST API
 */
public class LdapAccess {

  private Boolean manageGroupMembership;
  private Boolean view;
  private Boolean mapRoles;
  private Boolean impersonate;
  private Boolean manage;

  @Override
  public String toString() {
    return "LdapAccess{" +
        "manageGroupMembership=" + manageGroupMembership +
        ", view=" + view +
        ", mapRoles=" + mapRoles +
        ", impersonate=" + impersonate +
        ", manage=" + manage +
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
    LdapAccess that = (LdapAccess) o;
    return Objects.equals(manageGroupMembership, that.manageGroupMembership) &&
        Objects.equals(view, that.view) &&
        Objects.equals(mapRoles, that.mapRoles) &&
        Objects.equals(impersonate, that.impersonate) &&
        Objects.equals(manage, that.manage);
  }

  @Override
  public int hashCode() {

    return Objects.hash(manageGroupMembership, view, mapRoles, impersonate, manage);
  }

  public void setManageGroupMembership(Boolean manageGroupMembership) {
    this.manageGroupMembership = manageGroupMembership;
  }

  public void setView(Boolean view) {
    this.view = view;
  }

  public void setMapRoles(Boolean mapRoles) {
    this.mapRoles = mapRoles;
  }

  public void setImpersonate(Boolean impersonate) {
    this.impersonate = impersonate;
  }

  public void setManage(Boolean manage) {
    this.manage = manage;
  }

  public LdapAccess() {
  }

  private LdapAccess(Builder builder) {
    manageGroupMembership = builder.manageGroupMembership;
    view = builder.view;
    mapRoles = builder.mapRoles;
    impersonate = builder.impersonate;
    manage = builder.manage;
  }

  public static Builder builder() {
    return new Builder();
  }

  public Boolean getManageGroupMembership() {
    return manageGroupMembership;
  }

  public Boolean getView() {
    return view;
  }

  public Boolean getMapRoles() {
    return mapRoles;
  }

  public Boolean getImpersonate() {
    return impersonate;
  }

  public Boolean getManage() {
    return manage;
  }

  public static final class Builder {

    private Boolean manageGroupMembership;
    private Boolean view;
    private Boolean mapRoles;
    private Boolean impersonate;
    private Boolean manage;

    private Builder() {
    }

    public Builder withManageGroupMembership(Boolean val) {
      manageGroupMembership = val;
      return this;
    }

    public Builder withView(Boolean val) {
      view = val;
      return this;
    }

    public Builder withMapRoles(Boolean val) {
      mapRoles = val;
      return this;
    }

    public Builder withImpersonate(Boolean val) {
      impersonate = val;
      return this;
    }

    public Builder withManage(Boolean val) {
      manage = val;
      return this;
    }

    public LdapAccess build() {
      return new LdapAccess(this);
    }
  }
}
