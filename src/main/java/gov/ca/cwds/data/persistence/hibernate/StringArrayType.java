package gov.ca.cwds.data.persistence.hibernate;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

/**
 * Plagiarized from <a href=
 * "https://madhavivaram.wordpress.com/2015/06/12/mapping-array-column-of-postgres-in-hibernate/">here</a>.
 *
 * @author CWDS API Team
 */
public class StringArrayType implements UserType {

  private final ObjectMapper objectMapper = Jackson.newObjectMapper();
  private static final int[] arrayTypes = new int[] {Types.ARRAY};

  @Override
  public int[] sqlTypes() {
    return Arrays.copyOf(arrayTypes, arrayTypes.length);
  }

  @Override
  public Class<String[]> returnedClass() {
    return String[].class;
  }

  @Override
  public boolean equals(Object x, Object y) {
    return x == null ? y == null : x.equals(y);
  }

  @Override
  public int hashCode(Object x) {
    return x == null ? 0 : x.hashCode();
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
      Object owner) throws SQLException {
    String[] results = null;
    // Get the first column names.
    if (names != null && names.length > 0 && rs != null && rs.getArray(names[0]) != null) {

      Object array = rs.getArray(names[0]).getArray();
      if (array instanceof String[]) { // postgres
        results = (String[]) array;
      } else if (array instanceof Object[]) { // h2
        final Object[] objArray = (Object[]) array;
        try {
          results = objectMapper.readValue(
              ((String) objArray[0]).replace('{', '[').replace('}', ']'), String[].class);
        } catch (Exception e) {
          throw new SQLException("Cannot convert " + objArray[0] + " to String[]", e);
        }
      }
    }
    return results;
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index,
      SharedSessionContractImplementor session) throws SQLException {
    // Set the column with string array,
    if (value != null && st != null) {
      final String[] castObject = (String[]) value;
      final Array array = session.connection().createArrayOf("text", castObject);
      st.setArray(index, array);
    } else {
      st.setNull(index, arrayTypes[0]); // NOSONAR
    }
  }

  @Override
  public Object deepCopy(Object value) {
    return value == null ? null : ((String[]) value).clone();
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  @Override
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }

}
