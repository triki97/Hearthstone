DROP DATABASE IF EXISTS 201Stone;
CREATE DATABASE 201Stone;
USE 201Stone;

CREATE TABLE Users
(
Username varchar(255),
Password varchar(255)
);


CREATE TABLE StudentDeck
(
CardName varchar(255),
ManaCost int,
Effect varchar(255),
Attack int,
Defense int,
Quantity int,
CardId int PRIMARY KEY
);

CREATE TABLE ProfessorDeck
(
CardName varchar(255),
ManaCost int,
Effect varchar(255),
Attack int,
Defense int,
Quantity int,
CardId int PRIMARY KEY
);

LOAD DATA LOCAL INFILE '~/Dropbox/USC/CS\ 201/201stone/SQL\ Information/StudentDeckCSV.csv'
INTO TABLE StudentDeck FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE '~/Dropbox/USC/CS\ 201/201stone/SQL\ Information/ProfessorDeckCSV.csv'
INTO TABLE ProfessorDeck FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES;
