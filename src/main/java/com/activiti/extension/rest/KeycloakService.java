package com.activiti.extension.rest;

import com.activiti.extension.exceptions.CannotConnectException;
import com.activiti.extension.model.external.KeycloakGroup;
import com.activiti.extension.model.external.KeycloakToken;
import com.activiti.extension.model.external.KeycloakUser;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Alexander Mahabir
 *
 * Keycloak service to interact with the keycloak api for retrieving users.
 */
public interface KeycloakService {


  /**
   * Generic method to page through users in keycloak
   * @param first
   * @param max
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  Optional<List<KeycloakUser> > getUsers(
      int first,
      int max) throws CannotConnectException;

  /**
   * page through users in keycloak using a search filter
   *
   * @param first
   * @param max
   * @param search
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  Optional<List<KeycloakUser> > getUsersWithSearch(
      int first,
      int max,
      String search)
      throws CannotConnectException;

  /**
   * page through users in keycloak using a specified filter.
   * @param fist
   * @param max
   * @param firstName
   * @param lastName
   * @param username
   * @param email
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  Optional<List<KeycloakUser> > getUsersWithFilters(
      int fist,
      int max,
      String firstName,
      String lastName,
      String username,
      String email) throws CannotConnectException;


  /**
   * Get all groups
   * @return
   * @throws CannotConnectException
   */
  Optional<Set<KeycloakGroup>> getGroups() throws CannotConnectException;

  /**
   *  get all users belonging to a group
   * @param groupId
   * @param first
   * @param max
   * @return
   * @throws CannotConnectException
   */
  Optional<List<KeycloakUser>> getGroupMembers(String groupId, int first, int max) throws CannotConnectException;


  /**
   * connect to keycloak. this will return  a token, from which the beare information can be used
   * for all subsequent requests
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  KeycloakToken connect() throws CannotConnectException;

  /**
   * Get the total number of users from the keycloak repository
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  Integer getTotalUsers() throws CannotConnectException;

  /**
   * Get the keycloak bearer token
   */
  String getBearer() throws CannotConnectException;
}
