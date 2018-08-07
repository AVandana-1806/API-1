package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.knowm.sundial.Job;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.annotations.SimpleTrigger;
import org.knowm.sundial.exceptions.JobInterruptException;

import com.codahale.metrics.health.HealthCheck.Result;
import com.newrelic.api.agent.NewRelic;

import io.dropwizard.setup.Environment;

/**
 * Health check job.
 * 
 * @author CWDS API Team
 */
@SimpleTrigger(repeatInterval = 10, timeUnit = TimeUnit.MINUTES)
public class HealthcheckJob extends Job {

  /**
   * Default no-argument constructor.
   */
  public HealthcheckJob() {
    super();
  }

  @Override
  public void doRun() throws JobInterruptException {
    Environment environment =
        (Environment) SundialJobScheduler.getServletContext().getAttribute("environment");

    final Map<String, Result> healthCheckResults = environment.healthChecks().runHealthChecks();

    // A map of event data.
    // Key: String
    // Value: should be a String, Number, or Boolean.
    Map<String, Object> eventAttributes = new TreeMap<>();

    List<String> healthy = new ArrayList<>();
    List<String> unhealthy = new ArrayList<>();

    for (String key : healthCheckResults.keySet()) {
      Result result = healthCheckResults.get(key);
      if (result.isHealthy()) {
        healthy.add(key);
      } else {
        unhealthy.add(key);
      }
    }

    eventAttributes.put("healthy", healthy.toString());
    eventAttributes.put("unhealthy", unhealthy.toString());

    NewRelic.getAgent().getInsights().recordCustomEvent("FerbApiHealthCheck", eventAttributes);
  }
}
