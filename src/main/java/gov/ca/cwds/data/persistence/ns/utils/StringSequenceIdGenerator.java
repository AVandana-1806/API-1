package gov.ca.cwds.data.persistence.ns.utils;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import gov.ca.cwds.Identifiable;

/**
 * @author CWDS API Team
 */
public class StringSequenceIdGenerator implements IdentifierGenerator, Configurable {

  private String sequenceCallSyntax;

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
    final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
    final Dialect dialect = jdbcEnvironment.getDialect();

    final String sequencePerEntitySuffix =
        ConfigurationHelper.getString(SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX,
            params, SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX);

    final String defaultSequenceName =
        ConfigurationHelper.getBoolean(SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
            params, false) ? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix
                : SequenceStyleGenerator.DEF_SEQUENCE_NAME;

    String sequenceName = ConfigurationHelper.getString(SequenceStyleGenerator.SEQUENCE_PARAM,
        params, defaultSequenceName);

    if (sequenceName.contains(".")) {
      sequenceName = QualifiedNameParser.INSTANCE.parse(sequenceName).render();
    } else {
      final Identifier catalog = jdbcEnvironment.getIdentifierHelper().toIdentifier(
          ConfigurationHelper.getString(PersistentIdentifierGenerator.CATALOG, params));
      final Identifier schema = jdbcEnvironment.getIdentifierHelper().toIdentifier(
          ConfigurationHelper.getString(PersistentIdentifierGenerator.SCHEMA, params));
      sequenceName = new QualifiedNameParser.NameParts(catalog, schema,
          jdbcEnvironment.getIdentifierHelper().toIdentifier(sequenceName)).render();
    }

    sequenceCallSyntax = dialect.getSequenceNextValString(sequenceName);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object obj) {
    if (obj instanceof Identifiable) {
      Identifiable identifiable = (Identifiable) obj;
      Serializable id = (Serializable) identifiable.getId();
      if (id != null) {
        return id;
      }
    }

    final long seqValue =
        ((Number) Session.class.cast(session).createNativeQuery(sequenceCallSyntax).uniqueResult())
            .longValue();

    return String.valueOf(seqValue);
  }

}
