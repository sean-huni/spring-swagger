-- insert into party_identity.person(id, aristocratic_title, full_name, family_name, gender, birth_date, country_of_birth, death_date, created, last_updated, version) values (1, 'Elf', 'Sean', 'Huni', 0 , '1989-08-24', 'Zimbabwe', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP(), 1);
-- Update SEQ set next_val=1 where sequence_name = 'person';

select count(*)
from party_identity.person;