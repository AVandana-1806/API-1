package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import gov.ca.cwds.rest.util.Doofenshmirtz;

public class CaresSearchQueryTest extends Doofenshmirtz<CaresSearchQuery> {

  CaresSearchQuery target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();

    target = new CaresSearchQuery();
  }

  @Test
  public void type() throws Exception {
    assertThat(CaresSearchQuery.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getQuery_A$() throws Exception {
    setJson_A$String();
    final String actual = target.getQuery();
    final String expected =
        "{\"size\":\"250\",\"track_scores\":\"true\",\"sort\":[{\"_score\":\"desc\",\"last_name.keyword\":\"asc\",\"first_name.keyword\":\"asc\",\"_uid\":\"desc\"}],\"min_score\":\"2.5\",\"_source\":[\"id\",\"legacy_source_table\",\"first_name\",\"middle_name\",\"last_name\",\"name_suffix\",\"gender\",\"akas\",\"date_of_birth\",\"date_of_death\",\"ssn\",\"languages\",\"races\",\"ethnicity\",\"client_counties\",\"case_status\",\"addresses.id\",\"addresses.effective_start_date\",\"addresses.street_name\",\"addresses.street_number\",\"addresses.city\",\"addresses.county\",\"addresses.state_code\",\"addresses.zip\",\"addresses.type\",\"addresses.legacy_descriptor\",\"addresses.phone_numbers.number\",\"addresses.phone_numbers.type\",\"csec.start_date\",\"csec.end_date\",\"csec.csec_code_id\",\"csec.description\",\"sp_county\",\"sp_phone\",\"legacy_descriptor\",\"highlight\",\"phone_numbers.id\",\"phone_numbers.number\",\"phone_numbers.type\",\"estimated_dob_code\",\"sensitivity_indicator\",\"race_ethnicity\",\"open_case_responsible_agency_code\"],\"highlight\":{\"order\":\"score\",\"number_of_fragments\":\"10\",\"require_field_match\":\"false\",\"fields\":{\"autocomplete_search_bar\":{\"matched_fields\":[\"autocomplete_search_bar\",\"autocomplete_search_bar.phonetic\",\"autocomplete_search_bar.diminutive\"]},\"searchable_date_of_birth\":{}}},\"query\":{\"function_score\":{\"query\":{\"bool\":{\"must\":[{\"match\":{\"legacy_descriptor.legacy_table_name\":{\"query\":\"CLIENT_T\",\"_name\":\"q_cli\"}}}],\"filter\":[{\"match\":{\"gender\":{\"query\":\"unknown\",\"_name\":\"q_gender\"}}}]}},\"functions\":[{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"1_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"1_exact_first\"}}}]}},\"weight\":8192},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"2_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"2_exact_first\"}}},{\"match\":{\"name_suffix\":{\"query\":\"jr\",\"_name\":\"2_exact_suffix\"}}}]}},\"weight\":16384},{\"filter\":{\"multi_match\":{\"query\":\"aiden johnson\",\"operator\":\"and\",\"_name\":\"3_multi_aka\",\"fields\":[\"akas.first_name\",\"akas.last_name\"],\"type\":\"cross_fields\"}},\"weight\":4096},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"4_exact_last\"}}},{\"match\":{\"first_name.diminutive\":{\"query\":\"johnson\",\"_name\":\"4_diminutive_first\"}}}]}},\"weight\":2048},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"5_exact_last\"}}},{\"match\":{\"first_name.phonetic\":{\"query\":\"johnson\",\"_name\":\"5_phonetic_first\"}}}]}},\"weight\":1024},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"6_exact_last\"}}},{\"fuzzy\":{\"first_name\":{\"value\":\"johnson\",\"_name\":\"6_fuzzy_first\",\"fuzziness\":\"3\",\"prefix_length\":\"1\",\"max_expansions\":\"50\"}}}]}},\"weight\":512},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"7_exact_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"johnson\",\"minimum_should_match\":\"10%\",\"_name\":\"7_partial_first\"}}}]}},\"weight\":256},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"8_exact_last\"}}}],\"must_not\":[{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"8_no_match_first\"}}}]}},\"weight\":128},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"johnson\",\"_name\":\"9a_reverse_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"aiden\",\"_name\":\"9a_reverse_exact_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"johnson\",\"_name\":\"9b_reverse_partial_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"aiden\",\"minimum_should_match\":\"25%\",\"_name\":\"9b_reverse_partial_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"10_dupe_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"aiden\",\"_name\":\"10_dupe_exact_first\"}}}]}},\"weight\":32},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"first_name_ngram\":{\"query\":\"johnson\",\"minimum_should_match\":\"10%\",\"_name\":\"11_partial_first\"}}},{\"match\":{\"last_name_ngram\":{\"query\":\"aiden\",\"minimum_should_match\":\"15%\",\"_name\":\"11_partial_last\"}}}]}},\"weight\":16}],\"score_mode\":\"max\",\"boost_mode\":\"max\"}}}";

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setJson_A$String() throws Exception {
    String query =
        "{\"size\":\"250\",\"track_scores\":\"true\",\"sort\":[{\"_score\":\"desc\",\"last_name.keyword\":\"asc\",\"first_name.keyword\":\"asc\",\"_uid\":\"desc\"}],\"min_score\":\"2.5\",\"_source\":[\"id\",\"legacy_source_table\",\"first_name\",\"middle_name\",\"last_name\",\"name_suffix\",\"gender\",\"akas\",\"date_of_birth\",\"date_of_death\",\"ssn\",\"languages\",\"races\",\"ethnicity\",\"client_counties\",\"case_status\",\"addresses.id\",\"addresses.effective_start_date\",\"addresses.street_name\",\"addresses.street_number\",\"addresses.city\",\"addresses.county\",\"addresses.state_code\",\"addresses.zip\",\"addresses.type\",\"addresses.legacy_descriptor\",\"addresses.phone_numbers.number\",\"addresses.phone_numbers.type\",\"csec.start_date\",\"csec.end_date\",\"csec.csec_code_id\",\"csec.description\",\"sp_county\",\"sp_phone\",\"legacy_descriptor\",\"highlight\",\"phone_numbers.id\",\"phone_numbers.number\",\"phone_numbers.type\",\"estimated_dob_code\",\"sensitivity_indicator\",\"race_ethnicity\",\"open_case_responsible_agency_code\"],\"highlight\":{\"order\":\"score\",\"number_of_fragments\":\"10\",\"require_field_match\":\"false\",\"fields\":{\"autocomplete_search_bar\":{\"matched_fields\":[\"autocomplete_search_bar\",\"autocomplete_search_bar.phonetic\",\"autocomplete_search_bar.diminutive\"]},\"searchable_date_of_birth\":{}}},\"query\":{\"function_score\":{\"query\":{\"bool\":{\"must\":[{\"match\":{\"legacy_descriptor.legacy_table_name\":{\"query\":\"CLIENT_T\",\"_name\":\"q_cli\"}}}],\"filter\":[{\"match\":{\"gender\":{\"query\":\"unknown\",\"_name\":\"q_gender\"}}}]}},\"functions\":[{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"1_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"1_exact_first\"}}}]}},\"weight\":8192},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"2_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"2_exact_first\"}}},{\"match\":{\"name_suffix\":{\"query\":\"jr\",\"_name\":\"2_exact_suffix\"}}}]}},\"weight\":16384},{\"filter\":{\"multi_match\":{\"query\":\"aiden johnson\",\"operator\":\"and\",\"_name\":\"3_multi_aka\",\"fields\":[\"akas.first_name\",\"akas.last_name\"],\"type\":\"cross_fields\"}},\"weight\":4096},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"4_exact_last\"}}},{\"match\":{\"first_name.diminutive\":{\"query\":\"johnson\",\"_name\":\"4_diminutive_first\"}}}]}},\"weight\":2048},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"5_exact_last\"}}},{\"match\":{\"first_name.phonetic\":{\"query\":\"johnson\",\"_name\":\"5_phonetic_first\"}}}]}},\"weight\":1024},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"6_exact_last\"}}},{\"fuzzy\":{\"first_name\":{\"value\":\"johnson\",\"_name\":\"6_fuzzy_first\",\"fuzziness\":\"3\",\"prefix_length\":\"1\",\"max_expansions\":\"50\"}}}]}},\"weight\":512},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"7_exact_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"johnson\",\"minimum_should_match\":\"10%\",\"_name\":\"7_partial_first\"}}}]}},\"weight\":256},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"8_exact_last\"}}}],\"must_not\":[{\"match\":{\"first_name\":{\"query\":\"johnson\",\"_name\":\"8_no_match_first\"}}}]}},\"weight\":128},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"johnson\",\"_name\":\"9a_reverse_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"aiden\",\"_name\":\"9a_reverse_exact_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"johnson\",\"_name\":\"9b_reverse_partial_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"aiden\",\"minimum_should_match\":\"25%\",\"_name\":\"9b_reverse_partial_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"aiden\",\"_name\":\"10_dupe_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"aiden\",\"_name\":\"10_dupe_exact_first\"}}}]}},\"weight\":32},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"first_name_ngram\":{\"query\":\"johnson\",\"minimum_should_match\":\"10%\",\"_name\":\"11_partial_first\"}}},{\"match\":{\"last_name_ngram\":{\"query\":\"aiden\",\"minimum_should_match\":\"15%\",\"_name\":\"11_partial_last\"}}}]}},\"weight\":16}],\"score_mode\":\"max\",\"boost_mode\":\"max\"}}}";
    target.setJson(query);
  }

  @Test
  public void setQuery_A$JsonNode() throws Exception {
    JsonNode jsonNode = mock(JsonNode.class);
    target.setQuery(jsonNode);
  }

}
