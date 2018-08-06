package com.activiti.extension.services;

import com.activiti.extension.model.internal.ExternalGroup;

public interface GroupProcessor {


   /**
    * process will be called in parallel stream; ensure thread safety when used
    * @param group
    * @return
    */
   ExternalGroup process(ExternalGroup group);

}
