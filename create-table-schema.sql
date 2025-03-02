-- create table schema with regular columns, indexes, tsvector columns, triggers and gin indexes

-- create table
CREATE TABLE soccer (
    id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS  AS IDENTITY,
    team_name TEXT,
    league TEXT,
    wins INTEGER,
    losses INTEGER,
    draws INTEGER
);

--  create a tsvector column on the table to store the searchable text and a gin index
ALTER TABLE soccer ADD COLUMN search_vector_team_name tsvector;
ALTER TABLE soccer ADD COLUMN search_vector_league tsvector;

-- create triggers to update the columns with the data from the "team_name" and "league' columns using the to_tsvector
CREATE OR REPLACE TRIGGER tsvector_update_team_name
  BEFORE INSERT OR UPDATE ON soccer
  FOR EACH ROW EXECUTE FUNCTION tsvector_update_trigger(search_vector_team_name, 'pg_catalog.english', team_name);
  
CREATE OR REPLACE TRIGGER tsvector_update_league
  BEFORE INSERT OR UPDATE ON soccer
  FOR EACH ROW EXECUTE FUNCTION tsvector_update_trigger(search_vector_league, 'pg_catalog.english', league);

-- create a gin index on the  columns
CREATE INDEX soccer_search_team_name_idx ON soccer USING gin(search_vector_team_name);
CREATE INDEX soccer_search_league_idx ON soccer USING gin(search_vector_league);
