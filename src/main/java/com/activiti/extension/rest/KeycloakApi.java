package com.activiti.extension.rest;

import com.activiti.extension.model.external.KeycloakGroup;
import com.activiti.extension.model.external.KeycloakToken;
import com.activiti.extension.model.external.KeycloakUser;
import java.util.List;
import java.util.Set;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Keycloak API representation to be used with retrofit service
 */
public interface KeycloakApi {

  /**
   *
   * Retrieve a page of users
   *
   * @param realm
   * @param first user number
   * @param max max users per page
   * @return
   */
  @GET("admin/realms/{realm}/users")
  Call<List<KeycloakUser>> getUsers(@Path("realm") String realm,
      @Query("first") Integer first,
      @Query("max") Integer max);

  /**
   * Retrieve a page of users based on filter
   *
   * @param realm authentication realm
   * @param first starting user number
   * @param max maximum items per page
   * @param search A String contained in username, first or last name, or email
   * @return
   */
  @GET("admin/realms/{realm}/users")
  Call<List<KeycloakUser>> getUsers(@Path("realm") String realm,
      @Query("first") Integer first,
      @Query("max") Integer max,
      @Query("search") String search
  );

  /**
   * Get a list of all groups
   * @param realm
   * @return groups
   */
  @GET("admin/realms/{realm}/groups")
  Call<Set<KeycloakGroup>> getGroups(@Path("realm") String realm);


  /**
   * Retrieve paginated list of users who belong to a group
   * @param reaml
   * @param groupId
   * @param first
   * @param max
   * @return return a list of users
   */
  @GET("admin/realms/{realm}/groups/{id}/members")
  Call<List<KeycloakUser>> getGroupMembers(@Path("realm") String reaml, @Path("id") String groupId, @Query("first") Integer first, @Query("max") Integer max);

  /**
   * Retrieve page of users based on filters
   * @param realm authentication realm
   * @param first starting user number
   * @param max maximum items per page
   * @param username filter by username
   * @param lastName  filter by lastname
   * @param firstName filter by firstname
   * @param email fitler by email
   * @return
   */
  @GET("admin/realms/{realm}/users")
  Call<List<KeycloakUser>> getUsers(@Path("realm") String realm,
      @Query("first") Integer first,
      @Query("max") Integer max,
      @Query("username") String username,
      @Query("lastName") String lastName,
      @Query("firstName") String firstName,
      @Query("email") String email
  );

  /**
   * Retrieve an authentication token for authorized requests
   *
   * @param username username to authenticate with
   * @param password password to authenticate with
   * @param grant_type
   * @param clientId
   * @param realm authentication realm
   * @return
   */
  @FormUrlEncoded
  @POST("realms/{realm}/protocol/openid-connect/token")
  Call<KeycloakToken> authenticate(@Field("username") String username,
      @Field("password") String password, @Field("grant_type") String grant_type,
      @Field("client_id") String clientId, @Path("realm") String realm);


  /**
   *
   * get total users in the system
   *
   * @param realm authentication realm
   * @return
   */
  @GET("admin/realms/{realm}/users/count")
  Call<Integer> getTotalUsers(@Path("realm") String realm);
}
