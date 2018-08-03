package gov.ca.cwds.rest.services.hoi;

/**
 * <p>
 * This service handles a request to get relationships for the given client id.
 * <p>
 * 
 * @author CWDS API Team
 */
class HOIRelationshipTypeService {

  private HOIRelationshipTypeService() {
    throw new IllegalStateException("Utility class");
  }

  static boolean isRelationTypeParent(Short type) {
    final boolean fatherToChildRelationship =
        type <= 214 && type >= 203 && type != 206 && type != 212;
    final boolean motherToChildRelationship =
        type <= 254 && type >= 245 && type != 248 && type != 253;
    final boolean parentToChildRelationship = type == 243 || type == 5620 || type == 6361;
    return fatherToChildRelationship || motherToChildRelationship || parentToChildRelationship;
  }

  static boolean isRelationTypeChild(Short type) {
    final boolean daughterToParentRelationship =
        type <= 199 && type >= 188 && type != 191 && type != 197;
    final boolean sonToParentRelationship =
        type <= 293 && type >= 283 && type != 286 && type != 292;
    final boolean sonToMotherPresumedRelationship = type == 6360;
    final boolean childToParentRelationship = type == 301 || type == 242;
    return daughterToParentRelationship || sonToParentRelationship
        || sonToMotherPresumedRelationship || childToParentRelationship;
  }

  static boolean isRelationTypeSibling(Short type) {
    final boolean brotherToSiblingRelationship = type <= 184 && type >= 179;
    final boolean sisterToSiblingRelationship = type <= 281 && type >= 276;
    return brotherToSiblingRelationship || sisterToSiblingRelationship;
  }

  static boolean isParentChildOrSiblingRelationshipType(Short type) {
    return isRelationTypeParent(type) || isRelationTypeChild(type) || isRelationTypeSibling(type);
  }

}
