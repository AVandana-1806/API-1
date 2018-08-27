package gov.ca.cwds.data.persistence.ns.papertrail;

/**
 * Marks entity classes for PaperTrail.
 *
 * @author CWDS API Team
 */
@FunctionalInterface
public interface HasPaperTrail {

  /**
   * Return this entity's primary key value as a String.
   * 
   * @return primary key
   */
  String getId();

}
