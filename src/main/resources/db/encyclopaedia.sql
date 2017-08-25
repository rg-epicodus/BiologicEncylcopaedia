SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS kingdom (
 id int PRIMARY KEY auto_increment,
 kingdomName VARCHAR,
);

CREATE TABLE IF NOT EXISTS entry (
 id int PRIMARY KEY auto_increment,
 commonName VARCHAR,
);

CREATE TABLE IF NOT EXISTS personalNotes (
 id int PRIMARY KEY auto_increment,
 writtenBy VARCHAR,
 entryId INTEGER,
 personalNotesId INTEGER,
 content VARCHAR,
);

CREATE TABLE IF NOT EXISTS kingdom_entry (
 id int PRIMARY KEY auto_increment,
 kingdomId INTEGER,
 entryId INTEGER
);