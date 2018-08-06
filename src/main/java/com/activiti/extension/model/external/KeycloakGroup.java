package com.activiti.extension.model.external;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Alexander Mahabir
 *
 *
 *
 */
public class KeycloakGroup {

  private String id;
  private String name;
  private String path;
  private List<KeycloakGroup> subGroups;

  private KeycloakGroup(Builder builder) {
    id = builder.id;
    name = builder.name;
    path = builder.path;
    subGroups = builder.subGroups;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeycloakGroup that = (KeycloakGroup) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(path, that.path) &&
        Objects.equals(subGroups, that.subGroups);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, path, subGroups);
  }

  public boolean hasSubGroup () {
    return Optional.ofNullable(subGroups).isPresent();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public List<KeycloakGroup> getSubGroups() {
    return subGroups;
  }

  @Override
  public String toString() {
    return "KeycloakGroup{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", path='" + path + '\'' +
        ", subGroups=" + subGroups +
        '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setSubGroups(List<KeycloakGroup> subGroups) {
    this.subGroups = subGroups;
  }

  public static final class Builder {

    private String id;
    private String name;
    private String path;
    private List<KeycloakGroup> subGroups;

    private Builder() {
    }

    public Builder withId(String val) {
      id = val;
      return this;
    }

    public Builder withName(String val) {
      name = val;
      return this;
    }

    public Builder withPath(String val) {
      path = val;
      return this;
    }

    public Builder withSubGroups(List<KeycloakGroup> val) {
      subGroups = val;
      return this;
    }

    public KeycloakGroup build() {
      return new KeycloakGroup(this);
    }
  }
}
