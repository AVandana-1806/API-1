package gov.ca.cwds.es.live;

import java.io.Serializable;

import gov.ca.cwds.data.std.ApiLanguageAware;

public class TestLanguage implements ApiLanguageAware, Serializable {

  @Override
  public Integer getLanguageSysId() {
    return 1249;
  }

}
