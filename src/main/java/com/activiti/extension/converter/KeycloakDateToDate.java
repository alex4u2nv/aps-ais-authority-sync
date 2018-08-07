package com.activiti.extension.converter;

import com.google.common.base.Strings;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Mahabir
 *
 * Conversion service from a Keycloak ldap attributes date to a regular date object
 */
@Component
public class KeycloakDateToDate implements Converter<String[], Date> {

  private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssVV");
  private final Logger LOG = LoggerFactory.getLogger(KeycloakDateToDate.class);

  @Override
  public Date convert(String[] s) {
    if (s != null && s.length > 0 && !Strings.isNullOrEmpty(s[0])) {
      //using LDAP date format which typically uses zone-id 'Z'
      return Date
          .from(LocalDateTime.parse(s[0], FORMATTER).atZone(ZoneId.of("Z")).toInstant());
    }
    LOG.warn("Date is null");
    return null;
  }
}
