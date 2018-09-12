SET search_path TO 'DBN1SOC.intakens';

-- WARNING: addresses PK is varchar, not int4.

DO $$
<<block_seq_addresses>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.addresses ;
  SELECT setval('intakens.addresses_id_seq', v_max_id) into v_result_id;
END block_seq_addresses $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_addresses, nextval('intakens.addresses_id_seq') as next_id_addresses FROM intakens.addresses ;




DO $$
<<block_seq_agencies>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.agencies ;
  SELECT setval('intakens.agencies_id_seq', v_max_id) into v_result_id;
END block_seq_agencies $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_agencies, nextval('intakens.agencies_id_seq') as next_id_agencies FROM intakens.agencies ;



DO $$
<<block_seq_allegations>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.allegations;
  SELECT setval('intakens.allegations_id_seq', v_max_id) into v_result_id;
END block_seq_allegations $$;

COMMIT;

SELECT max("id") as max_id_allegations, nextval('intakens.allegations_id_seq') as next_id_allegations FROM intakens.allegations ;


-- WARNING: cross_reports PK is varchar, not int4.

DO $$
<<block_seq_cross_reports>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.cross_reports ;
  SELECT setval('intakens.cross_reports_id_seq', v_max_id) into v_result_id;
END block_seq_cross_reports $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_cross_reports, nextval('intakens.cross_reports_id_seq') as next_id_cross_reports FROM intakens.cross_reports ;



DO $$
<<block_seq_csec>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.csec;
  SELECT setval('intakens.csec_id_seq', v_max_id) into v_result_id;
END block_seq_csec $$;

COMMIT;

SELECT max("id") as max_id_csec, nextval('intakens.csec_id_seq') as next_id_csec FROM intakens.csec ;


DO $$
<<block_seq_intake_lov_categories>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.intake_lov_categories;
  SELECT setval('intakens.intake_lov_categories_id_seq', v_max_id) into v_result_id;
END block_seq_intake_lov_categories $$;

COMMIT;

SELECT max("id") as max_id_intake_lov_categories, nextval('intakens.intake_lov_categories_id_seq') as next_id_intake_lov_categories FROM intakens.intake_lov_categories ;


DO $$
<<block_seq_intake_only_lov_categories>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.intake_only_lov_categories;
  SELECT setval('intakens.intake_only_lov_categories_id_seq', v_max_id) into v_result_id;
END block_seq_intake_only_lov_categories $$;

COMMIT;

SELECT max("id") as max_id_intake_only_lov_categories, nextval('intakens.intake_only_lov_categories_id_seq') as next_id_intake_only_lov_categories FROM intakens.intake_only_lov_categories ;


DO $$
<<block_seq_legacy_descriptors>>
DECLARE
  v_max_id    INT8 := 0;
  v_result_id INT8 := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.legacy_descriptors;
  SELECT setval('intakens.legacy_descriptors_id_seq', v_max_id) into v_result_id;
END block_seq_legacy_descriptors $$;

COMMIT;

SELECT max("id") as max_id, nextval('intakens.legacy_descriptors_id_seq') as next_id, COUNT(*) AS TOTAL FROM intakens.legacy_descriptors FOR READ ONLY;


 
-- WARNING: participant_addresses PK is varchar, not int4.

DO $$
<<block_seq_participant_addresses>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.participant_addresses ;
  SELECT setval('intakens.participant_addresses_id_seq', v_max_id) into v_result_id;
END block_seq_participant_addresses $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_participant_addresses, nextval('intakens.participant_addresses_id_seq') as next_id_participant_addresses FROM intakens.participant_addresses ;


 
-- WARNING: participant_phone_numbers PK is varchar, not int4.

DO $$
<<block_seq_participant_phone_numbers>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.participant_phone_numbers ;
  SELECT setval('intakens.participant_phone_numbers_id_seq', v_max_id) into v_result_id;
END block_seq_participant_phone_numbers $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_participant_phone_numbers, nextval('intakens.participant_phone_numbers_id_seq') as next_id__participant_phone_numbers FROM intakens.participant_phone_numbers ;



-- WARNING: participants PK is varchar, not int4.

DO $$
<<block_seq_participants>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.participants ;
  SELECT setval('intakens.participants_id_seq', v_max_id) into v_result_id;
END block_seq_participants $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_participants, nextval('intakens.participants_id_seq') as next_id_participants FROM intakens.participants ;




-- WARNING: phone_numbers PK is varchar, not int4.

DO $$
<<block_seq_phone_numbers>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.phone_numbers;
  SELECT setval('intakens.phone_numbers_id_seq', v_max_id) into v_result_id;
END block_seq_phone_numbers $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_phone_numbers, nextval('intakens.phone_numbers_id_seq') as next_id_phone_numbers FROM intakens.phone_numbers  ;



-- WARNING: relationships PK is varchar, not int4.

DO $$
<<block_seq_relationships>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.relationships;
  SELECT setval('intakens.relationships_id_seq', v_max_id) into v_result_id;
END block_seq_relationships $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_relationships, nextval('intakens.relationships_id_seq') as next_id_relationships FROM intakens.relationships ;



-- WARNING: screening_addresses PK is varchar, not int4.

DO $$
<<block_seq_screening_addresses>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.screening_addresses;
  SELECT setval('intakens.screening_addresses_id_seq', v_max_id) into v_result_id;
END block_seq_screening_addresses $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_screening_addresses, nextval('intakens.screening_addresses_id_seq') as next_id_screening_addresses FROM intakens.screening_addresses ;



-- WARNING: Screenings PK is varchar, not int4.

DO $$
<<block_seq_screenings>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max(cast("id" AS INT4)) into v_max_id FROM intakens.screenings ;
  SELECT setval('intakens.screenings_id_seq', v_max_id) into v_result_id;
END block_seq_screenings $$;

COMMIT;

SELECT max(cast("id" AS INT4)) as max_id_screenings, nextval('intakens.screenings_id_seq') as next_id_screenings FROM intakens.screenings ;


DO $$
<<block_seq_system_codes>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.system_codes;
  SELECT setval('intakens.system_codes_id_seq', v_max_id) into v_result_id;
END block_seq_system_codes $$;

COMMIT;

SELECT max("id") as max_id_system_codes, nextval('intakens.system_codes_id_seq') as next_id_system_codes FROM intakens.system_codes ;


DO $$
<<block_seq_versions>>
DECLARE
  v_max_id    integer := 0;
  v_result_id integer := 0;
BEGIN
  SELECT max("id") into v_max_id FROM intakens.versions;
  SELECT setval('intakens.versions_id_seq', v_max_id) into v_result_id;
END block_seq_versions $$;

COMMIT;

SELECT max("id") as max_id_versions, nextval('intakens.versions_id_seq') as next_id_versions FROM intakens.versions ;

