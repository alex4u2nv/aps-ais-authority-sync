package com.activiti.extension.services.processors;

import com.activiti.domain.sync.ExternalIdmUser;
import com.activiti.extension.model.internal.ExternalUser;
import com.activiti.extension.services.UserProcessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Mahabir
 *
 * Process users based on last modified; and keep list for sync
 */

public class ProcessByLastModified implements UserProcessor {

  private List<ExternalIdmUser> users = new ArrayList<>();
  private Date lastModifiedTimestamp;

  public ProcessByLastModified(Date lastModifiedTimestamp) {
    this.lastModifiedTimestamp = lastModifiedTimestamp;
  }


  public List<ExternalIdmUser> getUsers() {
    return users;
  }

  /**
   * Check if users were created after last modified timestamp, then pass that list back as delta
   * for synchronizing.
   */
  @Override
  public boolean process(ExternalUser user) {
    if (lastModifiedTimestamp == null || user.getLastModifiedTimeStamp() == null) {
      users.add(user); //optimistically add this user.

    } else if (user.getLastModifiedTimeStamp() != null && lastModifiedTimestamp
        .before(user.getLastModifiedTimeStamp())) {
      users.add(user);
    }
    return true;
  }
}
