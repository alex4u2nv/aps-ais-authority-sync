package com.activiti.extension.services;

import com.activiti.extension.model.internal.ExternalUser;

public interface UserProcessor {

  /**
   * process will be called in parallel stream; ensure thread safety when used.
   * @param user
   * @return
   */
  boolean process(ExternalUser user);

}
