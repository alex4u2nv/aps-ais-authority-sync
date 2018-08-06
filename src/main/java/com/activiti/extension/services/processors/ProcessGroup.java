package com.activiti.extension.services.processors;

import com.activiti.extension.model.internal.ExternalGroup;
import com.activiti.extension.services.GroupProcessor;

public class ProcessGroup implements GroupProcessor {


  /**
   * process will be called in parallelStream; ensure threadSafety
   * @param group
   * @return
   */
  @Override
  public ExternalGroup process(ExternalGroup group) {
    return null;
  }
}
