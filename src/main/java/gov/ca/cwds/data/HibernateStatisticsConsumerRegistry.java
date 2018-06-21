package gov.ca.cwds.data;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.stat.Statistics;

/**
 * @author CWDS Intake Team
 */
public final class HibernateStatisticsConsumerRegistry {

  // one statistics consumer per bundle is currently enough
  private static Map<String, HibernateStatisticsConsumer> consumerMap = new HashMap<>();

  @FunctionalInterface
  public interface HibernateStatisticsConsumer {
    void consume(Statistics hibernateStatistics);
  }

  private HibernateStatisticsConsumerRegistry() {
    // no-op
  }

  public static void registerHibernateStatisticsConsumer(String bundleTag,
      HibernateStatisticsConsumer consumer) {
    consumerMap.put(bundleTag, consumer);
  }

  public static void unRegisterHibernateStatisticsConsumer(String bundleTag) {
    consumerMap.remove(bundleTag);
  }

  public static void provideHibernateStatistics(String bundleTag, Statistics hibernateStatistics) {
    if (consumerMap.containsKey(bundleTag)) {
      consumerMap.get(bundleTag).consume(hibernateStatistics);
    }
  }
}
