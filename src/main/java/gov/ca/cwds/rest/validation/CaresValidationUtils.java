package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.filters.RequestExecutionContext;

public class CaresValidationUtils {

  private CaresValidationUtils() {
    // static methods only.
  }

  public static boolean isBusinessValidationEnabled() {
    return RequestExecutionContext.instance().isXaTransaction()
        && "Y".equalsIgnoreCase(System.getProperty("DISABLE_BUSINESS_VALIDATION"));
  }

}
