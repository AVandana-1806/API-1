package gov.ca.cwds.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.stat.Statistics;

/**
 * Track Hibernate statistics per request and per session factory.
 * 
 * @author CWDS Intake Team
 */
public final class HibernateStatisticsConsumerRegistry {

  // One statistics consumer per bundle is currently enough
  private static Map<String, HibernateStatisticsConsumer> consumerMap = new ConcurrentHashMap<>();

  @FunctionalInterface
  public interface HibernateStatisticsConsumer {

    default void prepare(Statistics hibernateStatistics) {}

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

  public static void prepareHibernateStatisticsConsumer(String bundleTag,
      Statistics hibernateStatistics) {
    if (consumerMap.containsKey(bundleTag)) {
      consumerMap.get(bundleTag).prepare(hibernateStatistics);
    }
  }

  public static void provideHibernateStatistics(String bundleTag, Statistics hibernateStatistics) {
    if (consumerMap.containsKey(bundleTag)) {
      consumerMap.get(bundleTag).consume(hibernateStatistics);
    }
  }

}
