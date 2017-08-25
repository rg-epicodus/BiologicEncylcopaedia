SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS kingdom (
 id int PRIMARY KEY auto_increment,
 kingdomName VARCHAR,
);

CREATE TABLE IF NOT EXISTS entry (
 id int PRIMARY KEY auto_increment,
 commonName VARCHAR,
);

CREATE TABLE IF NOT EXISTS kingdom_entry (
 id int PRIMARY KEY auto_increment,
 kingdomid INTEGER,
 entryid INTEGER
);