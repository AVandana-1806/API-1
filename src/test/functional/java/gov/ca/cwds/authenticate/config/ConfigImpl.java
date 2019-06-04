package gov.ca.cwds.authenticate.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.ca.cwds.config.CwdsAuthenticationClientConfig;
import gov.ca.cwds.rest.ApiConfiguration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.Jackson;

/**
 * This class will overwrite the yaml file in api-core and read the API yaml file by implements
 * {@link YmlLoader}.
 * 
 * @author CWDS API Team
 *
 */
public class ConfigImpl implements YmlLoader {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigImpl.class);

  public static final String API_CONF_YML_PATH = "config/api.yml";
  public static final String FILE_PATH_KEY = "TEST_FILE_PATH";
  public CwdsAuthenticationClientConfig config;

  @Override
  public InputStream open(String path) throws IOException {
    final File file = new File(path);
    if (!file.exists()) {
      throw new FileNotFoundException("File " + file + " not found");
    }
    return new FileInputStream(file);
  }

  @Override
  public CwdsAuthenticationClientConfig readConfig() {
    config = loadTestConfiguratin();
    return config;
  }

  private CwdsAuthenticationClientConfig loadTestConfiguratin() {
    Yaml yaml = getYaml();
    String testConfFilePath = retrieveTestConfFilePath();
    return loadTestConfigurations(yaml, testConfFilePath);
  }

  private CwdsAuthenticationClientConfig loadTestConfigurations(Yaml yaml,
      String testConfFilePath) {
    CwdsAuthenticationClientConfig testConf = null;
    try (InputStream ymlTestingSourceProvider =
        new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
            .open(testConfFilePath);) {
      testConf = yaml.loadAs(ymlTestingSourceProvider, CwdsAuthenticationClientConfig.class);
    } catch (Exception e ) {
      LOGGER.error("Unable to convert test file to YAML. Check YML syntax and data structure against class.", e);
    }
    return testConf;
  }

  private String retrieveTestConfFilePath() {
    String testConfFilePath = "";
    try (InputStream ymlAppSourceProvider =
        new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
            .open(API_CONF_YML_PATH)) {
      ObjectMapper mapper = Jackson.newObjectMapper(new YAMLFactory());
      ApiConfiguration appConf = mapper.readValue(ymlAppSourceProvider, ApiConfiguration.class);

      String defaultLocation = appConf.getTestConfig().getConfigFile();
      testConfFilePath = getApiYmlPath(defaultLocation);
    } catch (IOException e) {
      LOGGER.error("Unable to retrieve the test config file path {}", e);
    }
    return testConfFilePath;
  }

  private String getApiYmlPath(String defaultPath) {
    String filepath = retreiveTestConfigFromEnvVar();
    LOGGER.info("default Test file path: " + defaultPath);
    return filepath.isEmpty() ? defaultPath : filepath;
  }

  private String retreiveTestConfigFromEnvVar() {
    String filePath = System.getenv().get(FILE_PATH_KEY);
    LOGGER.info("Overridden Test file path: " + filePath);
    return filePath != null ? filePath : "";
  }
}
