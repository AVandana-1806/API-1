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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.Identifiable;

/**
 * Vat is zis?
 * 
 * <p>
 * Some Postgres primary key fields are {@code VARCHAR} instead of {@code INT8}, yet the underlying
 * sequence increments a numeric counter, not a GUID. This class converts the numeric sequence value
 * to a String.
 * </p>
 * 
 * @author CWDS API Team
 */
public class StringSequenceIdGenerator implements IdentifierGenerator, Configurable {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringSequenceIdGenerator.class);

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
    LOGGER.debug("sequence SQL: {}", sequenceCallSyntax);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object obj) {
    if (obj instanceof Identifiable) {
      final Identifiable identifiable = (Identifiable) obj;
      final Serializable id = (Serializable) identifiable.getId();
      if (id != null) {
        return id;
      }
    }

    final long seqValue =
        ((Number) Session.class.cast(session).createNativeQuery(sequenceCallSyntax).uniqueResult())
            .longValue();

    LOGGER.info("Next key: {}", seqValue);
    return String.valueOf(seqValue);
  }

}
