package gov.ca.cwds.data;

import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.prepareHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.provideHibernateStatistics;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.registerHibernateStatisticsConsumer;
import static gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.unRegisterHibernateStatisticsConsumer;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import gov.ca.cwds.data.HibernateStatisticsConsumerRegistry.HibernateStatisticsConsumer;
import gov.ca.cwds.rest.core.Api;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.stat.Statistics;
import org.junit.Test;

public class HibernateStatisticsConsumerRegistryTest {

  private static final String STATISTICS_IS_PREPARED = "STATISTICS_IS_PREPARED";
  private static final String STATISTICS_IS_PROVIDED = "STATISTICS_IS_PROVIDED";

  @Test
  public void testHibernateStatisticsConsumerRegistry() {
    final Map<String, Boolean> testMarkers = new HashMap<>();
    testMarkers.put(STATISTICS_IS_PREPARED, false);
    testMarkers.put(STATISTICS_IS_PROVIDED, false);

    HibernateStatisticsConsumer consumer = new HibernateStatisticsConsumer() {
      @Override
      public void prepare(Statistics hibernateStatistics) {
        testMarkers.put(STATISTICS_IS_PREPARED, true);
      }

      @Override
      public void consume(Statistics hibernateStatistics) {
        testMarkers.put(STATISTICS_IS_PROVIDED, true);
      }
    };

    registerHibernateStatisticsConsumer(Api.DATASOURCE_CMS, consumer);

    prepareHibernateStatisticsConsumer(Api.DATASOURCE_CMS, null);
    assertTrue(testMarkers.get(STATISTICS_IS_PREPARED));
    assertFalse(testMarkers.get(STATISTICS_IS_PROVIDED));

    provideHibernateStatistics(Api.DATASOURCE_CMS, null);
    assertTrue(testMarkers.get(STATISTICS_IS_PROVIDED));

    unRegisterHibernateStatisticsConsumer(Api.DATASOURCE_CMS);
  }

}
