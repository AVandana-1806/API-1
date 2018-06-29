package gov.ca.cwds.rest.business.rules;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.rest.api.domain.cms.Allegation;
import gov.ca.cwds.rest.business.RuleAction;

/**
 * BUSINESS RULE: R - 08740
 *
 * <h2>Rule Text</h2> If 'Perpetrator' is selected as a Perpetrator Type, set Non Protecting Parent
 * Code to 'N'. If 'Perpetrator-SCP/Res. Facility Staff' is selected as a Perpetrator Type, set Non
 * Protecting Parent Code to 'P'. If 'Non-Protecting Parent' is selected as a Perpetrator Type, set
 * Non Protecting Parent Code to 'Y'. If 'Perpetrator Not Identified' is selected as a Perpetrator
 * Type, set Non Protecting Parent Code to 'U'.
 *
 * <h2>Access Logic</h2> If grdAllegations Perpetrator Type column = 'Perpetrator' set
 * ALLEGATION.Non_Protecting_Parent_Code to 'N'. If grdAllegations Perpetrator Type column =
 * 'Perpetrator-SCP/Res. Facility Staff' set ALLEGATION.Non_Protecting_Parent_Code to 'P'. If
 * grdAllegations Perpetrator Type column = 'Non-Protecting Parent' set
 * ALLEGATION.Non_Protecting_Parent_Code to 'Y'. If grdAllegations Perpetrator Type column =
 * 'Perpetrator Not Identified' set ALLEGATION.Non_Protecting_Parent_Code to 'U'.
 * 
 * CWDS API Team
 */
public class R08740SetNonProtectingParentCode implements RuleAction {

  private static final int CHILD_RESIDENTIAL_FACILITY_STAFF_REL_CODE = 5993;
  private static final int RESIDENTIAL_FACILITY_STAFF_REL_CODE_CHILD = 5994;
  private static final int MOTHER_DOUGHTER_PRESUMED = 5620;
  private static final int SON_MOTHER_PRESUMED_REL_CODE = 6360;
  private static final int MOTHER_SON_PRESUMED_REL_CODE = 6361;
  private static final int NON_CUSTODIAL_PARENT_DAUGHTER_REL_CODE = 272;
  private static final int NON_CUSTODIAL_PARENT_SON_REL_CODE = 273;

  private static final int DAUGHTER_PARENT_REL_CODE_START_INDEX = 187;
  private static final int DAUGHTER_PARENT_REL_CODE_END_INDEX = 200;
  private static final int SON_PARENT_REL_CODE_START_INDEX = 282;
  private static final int SON_PARENT_REL_CODE_END_INDEX = 294;
  private static final int FATHER_CHILD_REL_CODE_START_INDEX = 201;
  private static final int FATHER_CHILD_REL_CODE_END_INDEX = 214;
  private static final int MOTHER_CHILD_REL_CODE_START_INDEX = 245;
  private static final int MOTHER_CHILD_REL_CODE_END_INDEX = 254;

  private static final String UNIDENTIFIED_PERPETRATOR = "U";
  private static final String PERPETRATOR_IS_A_PARENT = "Y";
  private static final String PERPETRATOR_IS_NOT_A_PARENT = "N";
  private static final String RESIDENTIAL_FACILITY_STAFF_PERPETRATOR = "P";

  private ClientRelationshipDao clientRelationshipDao;

  private String victimClientId;
  private String perpatratorClientId;
  private Allegation cmsAllegation;

  /**
   * @param cmsAllegation - cmsAllegation
   * @param clientRelationshipDao - clientRelationshipDao
   * @param victimClientId - victimClientId
   * @param perpatratorClientId - perpatratorClientId
   */
  public R08740SetNonProtectingParentCode(gov.ca.cwds.rest.api.domain.cms.Allegation cmsAllegation,
      ClientRelationshipDao clientRelationshipDao, String victimClientId,
      String perpatratorClientId) {
    this.cmsAllegation = cmsAllegation;
    this.clientRelationshipDao = clientRelationshipDao;
    this.victimClientId = victimClientId;
    this.perpatratorClientId = perpatratorClientId;
  }

  @Override
  public void execute() {
    String nonProtectingParentCode;
    if (StringUtils.isEmpty(perpatratorClientId)) {
      nonProtectingParentCode = UNIDENTIFIED_PERPETRATOR;
    } else {
      nonProtectingParentCode = determineNonProtectingParentCode();
    }
    cmsAllegation.update(nonProtectingParentCode);
  }

  private String determineNonProtectingParentCode() {
    if (isResFacilityStaff()) {
      return RESIDENTIAL_FACILITY_STAFF_PERPETRATOR;
    } else {
      return isParent() ? PERPETRATOR_IS_A_PARENT : PERPETRATOR_IS_NOT_A_PARENT;
    }
  }

  private boolean isResFacilityStaff() {
    return isResFacilityStaffByPrimaryClientId() || isResFacilityStaffBySecondaryClientId();
  }

  private boolean isResFacilityStaffByPrimaryClientId() {
    return Arrays.stream(clientRelationshipDao.findByPrimaryClientId(victimClientId)).sequential()
        .anyMatch(relationship -> relationship.getSecondaryClientId().equals(perpatratorClientId)
            && relationship
                .getClientRelationshipType() == CHILD_RESIDENTIAL_FACILITY_STAFF_REL_CODE);
  }

  private boolean isResFacilityStaffBySecondaryClientId() {
    return Arrays.stream(clientRelationshipDao.findBySecondaryClientId(victimClientId)).sequential()
        .anyMatch(relationship -> relationship.getPrimaryClientId().equals(perpatratorClientId)
            && relationship
                .getClientRelationshipType() == RESIDENTIAL_FACILITY_STAFF_REL_CODE_CHILD);
  }

  private boolean isParent() {
    return isParentByPrimaryClientId() || isParentBySecondaryClientId();
  }

  private boolean isParentByPrimaryClientId() {
    return Arrays.stream(clientRelationshipDao.findByPrimaryClientId(victimClientId)).sequential()
        .anyMatch(relationship -> {
          Short type = relationship.getClientRelationshipType();
          return relationship.getSecondaryClientId().equals(perpatratorClientId)
              && checkParentRelationshipByPrimaryId(type);
        });
  }

  private boolean isParentBySecondaryClientId() {
    return Arrays.stream(clientRelationshipDao.findBySecondaryClientId(victimClientId)).sequential()
        .anyMatch(relationship -> {
          Short type = relationship.getClientRelationshipType();
          return relationship.getPrimaryClientId().equals(perpatratorClientId)
              && checkParentRelationBySecondaryId(type);
        });
  }

  private boolean checkParentRelationshipByPrimaryId(Short type) {
    boolean firstCondition =
        type <= DAUGHTER_PARENT_REL_CODE_END_INDEX && type >= DAUGHTER_PARENT_REL_CODE_START_INDEX;
    boolean secondCondition =
        type <= SON_PARENT_REL_CODE_END_INDEX && type >= SON_PARENT_REL_CODE_START_INDEX;
    boolean lastCondition = type == SON_MOTHER_PRESUMED_REL_CODE;
    return firstCondition || secondCondition || lastCondition;
  }

  private boolean checkParentRelationBySecondaryId(Short type) {
    boolean firstCondition =
        type <= FATHER_CHILD_REL_CODE_END_INDEX && type >= FATHER_CHILD_REL_CODE_START_INDEX;
    boolean secondCondition =
        type <= MOTHER_CHILD_REL_CODE_END_INDEX && type >= MOTHER_CHILD_REL_CODE_START_INDEX;
    boolean lastCondition =
        type == NON_CUSTODIAL_PARENT_DAUGHTER_REL_CODE || type == NON_CUSTODIAL_PARENT_SON_REL_CODE
            || type == MOTHER_DOUGHTER_PRESUMED || type == MOTHER_SON_PRESUMED_REL_CODE;
    return firstCondition || secondCondition || lastCondition;
  }

}
