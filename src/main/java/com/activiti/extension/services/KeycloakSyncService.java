package com.activiti.extension.services;

import com.activiti.extension.model.internal.ExternalGroup;
import com.activiti.extension.model.internal.ExternalObjects;
import com.activiti.extension.model.internal.ExternalUser;
import java.util.Set;

public interface KeycloakSyncService {


  /**
   * Process all users with UserProcessor::process
   */
  void processAllUsers(UserProcessor userProcessor, int batchSize) throws RuntimeException;

  void processAllUsersInGroup(UserProcessor processor, String groupId, int batchSize);

  Set<ExternalUser> getAllUsers() throws RuntimeException;

  Set<ExternalGroup> getAllGroups() throws RuntimeException;

  Set<ExternalGroup> getAllGroupAndMembers() throws RuntimeException;

  Set<ExternalUser> getAllUsersInGroup(String groupId) throws RuntimeException;
  /**
   *
   * @param start
   * @param max
   * @return
   * @throws RuntimeException
   */
  Set<ExternalUser> getUsers(int start, int max) throws RuntimeException;

  /**
   *
   * @return
   * @throws RuntimeException
   */
  ExternalObjects getAllUsersAndGroups() throws RuntimeException;

  /**
   *
   * @return
   * @throws RuntimeException
   */
  Integer getTotalUsers() throws RuntimeException;

}
