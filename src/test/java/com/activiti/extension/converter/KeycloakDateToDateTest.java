package com.activiti.extension.converter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexander Mahabir
 */
@RunWith(MockitoJUnitRunner.class)
public class KeycloakDateToDateTest {

  private final Logger LOG = LoggerFactory.getLogger(KeycloakDateToDateTest.class);

  @Spy
  KeycloakDateToDate keycloakDateToDate;

  /**
   * Test GMT date conversion used by ldap to generic java date object.
   * @throws Exception
   */
  @Test
  public void testKeycloakDateConversion() throws Exception {
    final String[] keycloakDate = {"20180802144725Z"};
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss z", Locale.ENGLISH);
    final Date gmtDate = sdf.parse("20180802144725 GMT");
    Date kcDate = keycloakDateToDate.convert(keycloakDate);
    assertThat(kcDate).isNotNull();
    assertThat(kcDate).isEqualTo(gmtDate);

  }
}
