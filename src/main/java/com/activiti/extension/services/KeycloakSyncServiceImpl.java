package com.activiti.extension.services;

import com.activiti.extension.converter.KeycloakGroupToGroup;
import com.activiti.extension.converter.KeycloakUserToUser;
import com.activiti.extension.model.external.KeycloakUser;
import com.activiti.extension.model.internal.ExternalGroup;
import com.activiti.extension.model.internal.ExternalObjects;
import com.activiti.extension.model.internal.ExternalUser;
import com.activiti.extension.rest.KeycloakService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Mahabir
 *
 * This service provides the methods to support the loading of users from Keycloak into the APS
 * External User hook
 *
 * Process all users supports the visitor pattern for processing and accumulating users based on
 * modified date timestamp. or any other critierior for processing that may be needed in the
 * future.
 */
@Service
public class KeycloakSyncServiceImpl implements KeycloakSyncService {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakSyncServiceImpl.class);
  @Autowired
  KeycloakService keycloakService;

  @Autowired
  KeycloakUserToUser keycloakUserToUser;

  @Autowired
  KeycloakGroupToGroup keycloakGroupToGroup;

  private static final int DEFAULT_BATCH = 100;


  /**
   * Process all users that are found in the keycloak database. Then pass all the users individually
   * to the UserProcess::process method
   *
   * If an error occurs we skip the batch, and move to the next list of items to process.
   *
   * @param userProcessor executes via parallelstream ensure thread safety processing is used.
   */
  @Override
  public void processAllUsers(UserProcessor userProcessor, int batchSize)
      throws RuntimeException {
    processAllUsersInGroup(userProcessor, null, batchSize);

  }

  /**
   * If group ID null; then process all users
   */
  public void processAllUsersInGroup(UserProcessor processor, String groupId, int batchSize) {
    int page = 0;
    int pageSize = 0;
    int totalCount = 0;
    int actualCount = 0;
    long startTime = System.nanoTime();
    do {
      Optional<List<KeycloakUser>> users =
          (groupId == null) ? keycloakService.getUsers(totalCount, batchSize)
              : keycloakService.getGroupMembers(groupId, totalCount, batchSize);
      if (users.isPresent()) {
        List<ExternalUser> externalUsers = users.get()
            .stream()
            .map(user -> keycloakUserToUser.convert(user)).collect(
                Collectors.toList());
        pageSize = externalUsers.size();
        totalCount += pageSize;
        actualCount += pageSize;
        externalUsers.parallelStream().forEach(user -> processor.process(user));
        LOGGER.debug("page: {} with items returned {}", page, pageSize);
      } else {
        totalCount += batchSize; //increment by batch size as this batch has an invalid item that prevents keycloak form returning
        LOGGER.error("No users were returned, check Keycloak's logs for possible errors");
      }
      page++;

    } while (pageSize == batchSize);
    long endTime = System.nanoTime();
    long duration = (endTime - startTime) / 1000000;
    LOGGER.info("-------------------------------------------------------------------------------");

    LOGGER.info("Processed a total of {} users in {} ms", actualCount, duration);

    int existingCount = keycloakService.getTotalUsers();
    LOGGER.info("There are currently {} users in keycloak", existingCount);

    LOGGER.info("-------------------------------------------------------------------------------");


  }

  /**
   * fetch all users from keycloak. Batchsize is used when iterating keycloak
   */
  @Override
  public Set<ExternalUser> getAllUsers() throws RuntimeException {

    final Set<ExternalUser> users = ConcurrentHashMap.newKeySet();
    processAllUsers((e) -> users.add(e), DEFAULT_BATCH);

    return users;
  }

  @Override
  public Set<ExternalUser> getAllUsersInGroup(String groupId) throws RuntimeException {
    Set<ExternalUser> members = ConcurrentHashMap.newKeySet();

    processAllUsersInGroup((e) -> members.add(e), groupId, DEFAULT_BATCH);
    return members;
  }

  /**
   * get all user and group memberships
   * @return
   * @throws RuntimeException
   */
  @Override
  public ExternalObjects getAllUsersAndGroups() throws RuntimeException {

    Set<ExternalUser> users = getAllUsers();
    Set<ExternalGroup> groups =getAllGroupAndMembers();

    return ExternalObjects.builder()
        .withGroups(new ArrayList<>(groups))
        .withUsers(new ArrayList(users))
        .build();
  }

  @Override
  public Set<ExternalGroup> getAllGroupAndMembers() throws RuntimeException {
    Set<ExternalGroup> groups = getAllGroups();
    groups.parallelStream().forEach(group ->
        getGroupMembers(group));
    return groups;
  }

  /**
   * Collect group members for a group; also traverse down childGroups and popuplate those as well.
   * //recursive.
   * @param group
   * @return
   */
  private ExternalGroup getGroupMembers(ExternalGroup group) {
    List<ExternalUser> ug = new ArrayList<>(getAllUsersInGroup(group.getOriginalSrcId()));
    group.setUsers(ug);
    if (group.hasChildGroups()) {
      group.getChildGroups().parallelStream()
          .forEach(cGroup -> getGroupMembers((ExternalGroup) cGroup));
    }
    return group;
  }

  /**
   * Return all Groups found in keycloak
   */
  @Override
  public Set<ExternalGroup> getAllGroups() throws RuntimeException {
    final Set<ExternalGroup> externalGroups = ConcurrentHashMap.newKeySet();
    keycloakService.getGroups().ifPresent(groups ->
        externalGroups.addAll(
            groups.parallelStream()
                .map(keycloakGroup -> keycloakGroupToGroup.convert(keycloakGroup))
                .collect(Collectors.toSet()))

    );

    return externalGroups;
  }

  /**
   * Iterate through the users in keycloak
   */
  @Override
  public Set<ExternalUser> getUsers(int start, int max) throws RuntimeException {
    Optional<List<KeycloakUser>> optionalUsers = keycloakService.getUsers(start, max);
    Set<ExternalUser> extUsers = ConcurrentHashMap.newKeySet();
    if (optionalUsers.isPresent()) {
      List<KeycloakUser> users = optionalUsers.get();
      extUsers = users.parallelStream()
          .map(user -> keycloakUserToUser.convert(user)).collect(
              Collectors.toSet());
    } else {
      LOGGER.error("Keycloak Service did not return any users; Check keycloak logs for errors");
    }
    return extUsers;
  }

  /**
   * Get the total number of users that are in keycloak
   */
  @Override
  public Integer getTotalUsers() throws RuntimeException {
    return keycloakService.getTotalUsers();
  }
}
