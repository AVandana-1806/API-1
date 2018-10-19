package gov.ca.cwds.rest.messages;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueDetailsCreator;
import gov.ca.cwds.rest.exception.IssueType;
import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Facility to relay errors from persistence and service layers back to a domain response.
 * 
 * <p>
 * This implementation is <strong>NOT thread safe!</strong> Construct an instance as needed per
 * request or use the MessageBuilder provided in
 * {@link RequestExecutionContext#getMessageBuilder()}.
 * </p>
 * 
 * @author CWDS API Team
 * @see ErrorMessage
 */
public class MessageBuilder {

  private static final Map<ErrorType, IssueType> errorTypeTranslation;

  /**
   * Map Ferb ErrorType to IssueType.
   */
  static {
    errorTypeTranslation = new EnumMap<>(ErrorType.class);
    errorTypeTranslation.put(ErrorType.BUSINESS, IssueType.BUSINESS_VALIDATION);
    errorTypeTranslation.put(ErrorType.CLIENT_AUTHORIZATION, IssueType.DATA_ACCESS_EXCEPTION);
    errorTypeTranslation.put(ErrorType.CLIENT_CONTRACT, IssueType.JSON_PROCESSING_EXCEPTION);
    errorTypeTranslation.put(ErrorType.DATA_ACCESS, IssueType.DATA_ACCESS_EXCEPTION);
    errorTypeTranslation.put(ErrorType.VALIDATION, IssueType.CONSTRAINT_VALIDATION);
  }

  private List<ErrorMessage> messages = new ArrayList<>();
  private Set<IssueDetails> issues = new LinkedHashSet<>();

  /**
   * messageBuilder - messageBuilder
   */
  public MessageBuilder() {
    // default constructor
  }

  public void merge(MessageBuilder messageBuilder) {
    this.messages.addAll(messageBuilder.messages);
    this.issues.addAll(messageBuilder.issues);
  }

  /**
   * @param message - message
   */
  public void addError(String message) {
    addError(message, ErrorMessage.ErrorType.VALIDATION);
  }

  /**
   * @param message - message
   * @param type - type
   */
  public void addError(String message, ErrorType type) {
    messages.add(new ErrorMessage(type, message, ""));

    final IssueDetails issue = new IssueDetails();
    this.issues.add(issue);
    issue.setUserMessage(message);
    issue.setType(errorTypeTranslation.containsKey(type) ? errorTypeTranslation.get(type)
        : IssueType.UNEXPECTED_EXCEPTION);
  }

  /**
   * @param errors - errors
   */
  public void addDomainValidationError(Set<ConstraintViolation<DomainObject>> errors) {
    if (!errors.isEmpty()) {
      errors.forEach(error -> {
        final String message = error.getPropertyPath() + " " + error.getMessage();
        messages.add(new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, message, ""));
      });

      errors.forEach(error -> issues.add(IssueDetailsCreator.create(error, null)));
    }
  }
  
  /**
   * @param issueDetails - Set of IssueDetails
   * @param type - Error Type
   */
  public void addIssueDetails(Set<IssueDetails> issueDetails, ErrorType type) {
    if (!issueDetails.isEmpty()) {
      for (IssueDetails issueDetail : issueDetails) {
        this.issues.add(issueDetail);
        this.messages.add(new ErrorMessage(type, issueDetail.getUserMessage(), ""));
      }
    }    
  }

  /**
   * @return the error message
   */
  public List<ErrorMessage> getMessages() {
    return messages == null ? new ArrayList<>() : messages;
  }

  public void addMessageAndLog(String message, org.slf4j.Logger logger) {
    addError(message);
    logger.error(message);
  }

  public void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger) {
    addError(message);
    logger.error(message, exception.getMessage());
  }

  public void addMessageAndLog(String message, Exception exception, org.slf4j.Logger logger,
      ErrorType type) {
    addError(message, type);
    logger.error(message, exception.getMessage());
  }

  public Set<IssueDetails> getIssues() {
    return issues;
  }
}
