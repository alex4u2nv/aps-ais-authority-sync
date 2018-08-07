package com.activiti.extension.converter;

import com.activiti.extension.model.external.KeycloakGroup;
import com.activiti.extension.model.internal.ExternalGroup;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KeycloakGroupToGroup implements Converter<KeycloakGroup, ExternalGroup> {

  @Override
  public ExternalGroup convert(KeycloakGroup keycloakGroup) {
    if (Optional.ofNullable(keycloakGroup).isPresent()) {
      return ExternalGroup.builder()
          .withOriginalSrcId(keycloakGroup.getId())
          .withChildGroups(
              keycloakGroup
                  .getSubGroups()
                  .stream()
                  .map(this::convert)
                  .collect(Collectors.toList()))
          .withName(keycloakGroup.getName())
          .build();
    }
    return null;
  }
}
