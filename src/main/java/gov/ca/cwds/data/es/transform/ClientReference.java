package gov.ca.cwds.data.es.transform;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Id;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Designates a direct child class of Client, that has a foreign key to its parent.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"squid:S1206"})
public abstract class ClientReference extends ApiObjectIdentity implements PersistentObject {

  private static final long serialVersionUID = 1L;

  protected enum ColumnPosition {
    START, CLT_IDENTIFIER, CLT_LST_UPD_ID, CLT_LST_UPD_TS, CLT_ADJDEL_IND, CLT_ADPTN_STCD, CLT_ALN_REG_NO, CLT_BIRTH_CITY, CLT_B_CNTRY_C, CLT_BIRTH_DT, CLT_BR_FAC_NM, CLT_B_STATE_C, CLT_BP_VER_IND, CLT_CHLD_CLT_B, CLT_CL_INDX_NO, CLT_COMMNT_DSC, CLT_COM_FST_NM, CLT_COM_LST_NM, CLT_COM_MID_NM, CLT_CONF_ACTDT, CLT_CONF_EFIND, CLT_CREATN_DT, CLT_CURRCA_IND, CLT_COTH_DESC, CLT_CURREG_IND, CLT_DEATH_DT, CLT_DTH_DT_IND, CLT_DEATH_PLC, CLT_DTH_RN_TXT, CLT_DRV_LIC_NO, CLT_D_STATE_C, CLT_EMAIL_ADDR, CLT_EST_DOB_CD, CLT_ETH_UD_CD, CLT_FTERM_DT, CLT_GENDER_CD, CLT_HEALTH_TXT, CLT_HISP_UD_CD, CLT_HISP_CD, CLT_I_CNTRY_C, CLT_IMGT_STC, CLT_INCAPC_CD, CLT_HCARE_IND, CLT_LIMIT_IND, CLT_LITRATE_CD, CLT_MAR_HIST_B, CLT_MRTL_STC, CLT_MILT_STACD, CLT_MTERM_DT, CLT_NMPRFX_DSC, CLT_NAME_TPC, CLT_OUTWRT_IND, CLT_PREVCA_IND, CLT_POTH_DESC, CLT_PREREG_IND, CLT_P_ETHNCTYC, CLT_P_LANG_TPC, CLT_RLGN_TPC, CLT_S_LANG_TC, CLT_SNTV_HLIND, CLT_SENSTV_IND, CLT_SOCPLC_CD, CLT_SOC158_IND, CLT_SSN_CHG_CD, CLT_SS_NO, CLT_SUFX_TLDSC, CLT_TRBA_CLT_B, CLT_TR_MBVRT_B, CLT_UNEMPLY_CD, CLT_ZIPPY_IND, CLT_IBMSNAP_LOGMARKER, CLT_IBMSNAP_OPERATION
  }

  @Id
  @Column(name = "CLT_IDENTIFIER")
  protected String cltId;

  public ClientReference read(ResultSet rs) throws SQLException {
    this.cltId = trimToNull(rs.getString(ColumnPosition.CLT_IDENTIFIER.ordinal()));
    return this;
  }

  public String getCltId() {
    return cltId;
  }

  public void setCltId(String cltId) {
    this.cltId = cltId;
  }

  @Override
  public int hashCode() {
    return 31 * ((getCltId() == null) ? 0 : getCltId().hashCode());
  }

}
