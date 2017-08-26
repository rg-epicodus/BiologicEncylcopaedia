SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS kingdom (
 organismId int PRIMARY KEY auto_increment,
 kingdomName VARCHAR,
);

CREATE TABLE IF NOT EXISTS personalNotes (
 id int PRIMARY KEY auto_increment,
 writtenBy VARCHAR,
 content VARCHAR,
);

CREATE TABLE IF NOT EXISTS entry (
 entryId int PRIMARY KEY auto_increment,
 commonName VARCHAR,
 phylum VARCHAR,
 kingdomId INTEGER,
);

CREATE TABLE IF NOT EXISTS kingdom_personalNotes (
 id int PRIMARY KEY auto_increment,
 kingdomId INTEGER,
 personalNotesId INTEGER,
 entryId INTEGER,
);