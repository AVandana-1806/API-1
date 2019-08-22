package gov.ca.cwds.rest.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import gov.ca.cwds.rest.api.domain.CaresSearchQuery;

public class CaresRawJsonDeserialzier extends JsonDeserializer<CaresSearchQuery> {

  @Override
  public CaresSearchQuery deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    final TreeNode tree = jp.getCodec().readTree(jp);
    CaresSearchQuery bean = new CaresSearchQuery();
    bean.setJson(tree.toString());
    return bean;
  }

}
