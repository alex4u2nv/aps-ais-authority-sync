package com.activiti.extension.model.external;

import java.util.Objects;

/**
 * @author Alexander Mahabir
 */
public class KeycloakToken {

  private String access_token;
  private Integer expires_in;
  private Integer refresh_expires_in;
  private String refresh_token;
  private String token_type;
  private Integer not_before_policy;
  private String session_state;

  private KeycloakToken(Builder builder) {
    access_token = builder.access_token;
    expires_in = builder.expires_in;
    refresh_expires_in = builder.refresh_expires_in;
    refresh_token = builder.refresh_token;
    token_type = builder.token_type;
    not_before_policy = builder.not_before_policy;
    session_state = builder.session_state;
  }

  public static Builder builder() {
    return new Builder();
  }

  public KeycloakToken() {
  }

  @Override
  public String toString() {
    return "KeycloakToken{" +
        "access_token='" + access_token + '\'' +
        ", expires_in=" + expires_in +
        ", refresh_expires_in=" + refresh_expires_in +
        ", refresh_token=" + refresh_token +
        ", token_type='" + token_type + '\'' +
        ", not_before_policy=" + not_before_policy +
        ", session_state='" + session_state + '\'' +
        '}';
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public Integer getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(Integer expires_in) {
    this.expires_in = expires_in;
  }

  public Integer getRefresh_expires_in() {
    return refresh_expires_in;
  }

  public void setRefresh_expires_in(Integer refresh_expires_in) {
    this.refresh_expires_in = refresh_expires_in;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  public Integer getNot_before_policy() {
    return not_before_policy;
  }

  public void setNot_before_policy(Integer not_before_policy) {
    this.not_before_policy = not_before_policy;
  }

  public String getSession_state() {
    return session_state;
  }

  public void setSession_state(String session_state) {
    this.session_state = session_state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeycloakToken that = (KeycloakToken) o;
    return Objects.equals(access_token, that.access_token) &&
        Objects.equals(expires_in, that.expires_in) &&
        Objects.equals(refresh_expires_in, that.refresh_expires_in) &&
        Objects.equals(refresh_token, that.refresh_token) &&
        Objects.equals(token_type, that.token_type) &&
        Objects.equals(not_before_policy, that.not_before_policy) &&
        Objects.equals(session_state, that.session_state);
  }

  @Override
  public int hashCode() {

    return Objects.hash(access_token, expires_in, refresh_expires_in, refresh_token, token_type,
        not_before_policy, session_state);
  }

  public static final class Builder {

    private String access_token;
    private Integer expires_in;
    private Integer refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private Integer not_before_policy;
    private String session_state;

    private Builder() {
    }

    public Builder withAccess_token(String val) {
      access_token = val;
      return this;
    }

    public Builder withExpires_in(Integer val) {
      expires_in = val;
      return this;
    }

    public Builder withRefresh_expires_in(Integer val) {
      refresh_expires_in = val;
      return this;
    }

    public Builder withRefresh_token(String val) {
      refresh_token = val;
      return this;
    }

    public Builder withToken_type(String val) {
      token_type = val;
      return this;
    }

    public Builder withNot_before_policy(Integer val) {
      not_before_policy = val;
      return this;
    }

    public Builder withSession_state(String val) {
      session_state = val;
      return this;
    }

    public KeycloakToken build() {
      return new KeycloakToken(this);
    }
  }
}
