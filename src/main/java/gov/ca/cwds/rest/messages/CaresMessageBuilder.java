package gov.ca.cwds.rest.messages;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.exception.IssueDetails;

public interface CaresMessageBuilder {

  void merge(MessageBuilder messageBuilder);

  /**
   * @param message - message
   */
  void addError(String message);

  /**
   * @param message - message
   * @param type - type
   */
  void addError(String message, ErrorType type);

  /**
   * @param errors - errors
   */
  void addDomainValidationError(Set<ConstraintViolation<DomainObject>> errors);

  /**
   * @return the error message
   */
  List<ErrorMessage> getMessages();

  void addMessageAndLog(String message, org.slf4j.Logger logger);

  void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger);

  void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger,
      ErrorType type);

  Set<IssueDetails> getIssues();

}
