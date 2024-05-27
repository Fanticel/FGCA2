create schema FGCADB;
set schema 'fgcadb';

CREATE TABLE UserTable(
    userName varchar(25) PRIMARY KEY ,
    password varchar(25),
    BRP int,
    displayName varchar(25),
    role varchar(20)
);
drop table event cascade ;
CREATE TABLE Event(
    title varchar(50) primary key,
    game varchar(25),
    minBRP int,
    maxBRP int,
    status varchar(15),
    maxParticipants int,
    date date,
    startingHour int,
    organiserName varchar(25),
    FOREIGN KEY (organiserName) REFERENCES usertable(userName)
);
DELETE FROM Match where userName1='a' OR username2='a';
drop table match cascade ;
CREATE TABLE Match(
    eventTitle varchar(50),
    userName1 varchar(25),
    userName2 varchar(25),
    user1Score int,
    user2Score int,
    arrayPosition int,
    PRIMARY KEY (eventTitle, userName1, userName2),
    FOREIGN KEY (eventTitle) REFERENCES Event(title),
    FOREIGN KEY (userName1) REFERENCES UserTable(userName),
    FOREIGN KEY (userName2) REFERENCES UserTable(userName)
);
drop table participants cascade ;
CREATE TABLE participants(
    userName varchar(25),
    eventTitle varchar(50),
    checkInStatus boolean,
    primary key (userName, eventTitle),
    FOREIGN KEY (userName) REFERENCES usertable(userName),
    FOREIGN KEY (eventTitle) REFERENCES event(title)
);
SELECT * FROM playermatches;
CREATE TABLE PlayerMatches(
    matchID serial primary key,
    userName1 varchar(25),
    userName2 varchar(25),
    user1Score int,
    user2Score int,
    FOREIGN KEY (userName1) REFERENCES UserTable(userName),
    FOREIGN KEY (userName2) REFERENCES UserTable(userName)
);
INSERT INTO games VALUES ('Dragon Ball FighterZ','Dragon Ball FighterZ is a 3v3 fighting game developed by Arc System Works based on the Dragon Ball franchise. It uses 3D models to simulate 2D art from the anime, manga, and films.');
CREATE TABLE games(
    gameName varchar(50),
    description varchar(1000),
    PRIMARY KEY (gameName)
);
SELECT * FROM games;
INSERT INTO characters VALUES (1,'Android 16','Big range | Scary mid screen | Mix-Up Game','Needy | Committal | Outclassed','Android 16 is a mix-up focused character that, while having relatively weak neutral without meter or assists, makes up for it with solid strike/throw mix featuring an unreactable command grab in 236L, as well as a fantastic pressure reset tool in 214S to reinforce it. ','Dragon Ball FighterZ'),
                              (2,'Android 17','Terrifying pressure |  Great Battery | Special Assist','Rekka conditioning | Poor range','Android 17 is a fast, heavy mix-up based character that possesses a combination of various mobility options and rekka moves that grants him an overall constant pressure, fantastic mix-up and unorthodox combo routes that lead to very high damage. With just an assist and half a bar to hand, an unscaled starter can lead to colossal punishment, and he will continue chasing you after you get back up with Accel Driver and Finishing Driver: two rekkas with a huge tray of different follow-ups.','Dragon Ball FighterZ');
DROP TABLE characters CASCADE ;
CREATE TABLE characters(
    id int,
    name varchar(100) ,
    pros varchar(500),
    cons varchar(500),
    overview varchar(1000),
    originGameName varchar(50),
    PRIMARY KEY (id),
    FOREIGN KEY (originGameName) REFERENCES  games(gameName)
);
SELECT * FROM characters;
INSERT INTO characterMoves VALUES (1, '5M', '700', 'All', null, '11','3','17','-4',null),
                                  (1, '5H', '850/1000', 'All', 'U1', '15','11','21','-8','13-25 Strike Armor'),
                                  (1, '5S', '600x1~2', 'All', null, '10',null,'total 50','-10',null),
                                  (1, '2L', '400', 'Low', null, '8','3','13','-4',null),
                                  (1, '2M', '700', 'Low', null, '13','4','24','-12',null),
                                  (1, '2H', '850/1000', 'All', 'U1+', '15','9','23','-22','4-23 Head, 15-23 Strike Armor');
DROP TABLE characterMoves;
CREATE TABLE characterMoves(
    characterId int,
    moveName varchar(50),
    damage varchar(50),
    guard varchar(50),
    smash varchar(50),
    startup varchar(50),
    active varchar(50),
    recovery varchar(50),
    onBlock varchar(50),
    invulnerability varchar(50),
    PRIMARY KEY (characterId, moveName),
    FOREIGN KEY (characterId) REFERENCES characters(id)
);
SELECT * FROM characterMoves;
drop table usertable cascade ;
INSERT INTO usertable VALUES ('SamplePlayer1', '1234', 2000, 'Fantic', 'user'),
                             ('SamplePlayer2', '4321', 2100, 'Baltyk', 'user'),
                             ('SamplePlayer3', '2334', 1500, 'Caltek', 'user'),
                             ('SampleModerator', '5678', 100, 'TvojaMama', 'moderator');
INSERT INTO fgcadb.usertable VALUES ('a', '12345', 130, 'test', 'user');
UPDATE usertable SET role='moderator' WHERE userName='?';
SELECT * FROM usertable;
DELETE FROM event;
INSERT INTO event VALUES ('Sample event3', 'Tekken69', 1500, 2500, 'In progress', 16, date('2024-05-09'), 9, 'SampleModerator');
SELECT * FROM event;

INSERT INTO participants VALUES ('SamplePlayer1', 'Sample event', true),
                                ('SamplePlayer2', 'Sample event', true),
                                ('SamplePlayer3', 'Sample event', true),
                                ('a', 'The best tournament ever', true);
SELECT * FROM participants;
UPDATE participants SET checkInStatus=true WHERE  eventTitle='The best tournament ever';
INSERT INTO participants VALUES ('a','The best tournament ever',true);
INSERT INTO match VALUES ('Sample event3', 'SamplePlayer2', 'a', 0, 0);

SELECT * FROM Event;
UPDATE event SET status='In progress' WHERE title='The best tournament ever';
UPDATE event SET date = date('2024-05-16') WHERE title = 'The best tournament ever';
UPDATE event SET startingHour=9 WHERE title='The best tournament ever';
UPDATE event SET minBRP=0 WHERE title='The best tournament ever';
UPDATE event SET maxBRP=9999 WHERE title='The best tournament ever';
UPDATE event SET organiserName='SampleModerator' WHERE title='The best tournament ever';

DELETE FROM match WHERE eventTitle = 'The best tournament ever';
SELECT * FROM match WHERE eventTitle = 'The best tournament ever' ORDER BY arrayPosition;
INSERT INTO match VALUES ('The best tournament ever', 'SampleModerator', 'SamplePlayer2', 2, 0, 1),
                         ('The best tournament ever', 'SamplePlayer3', 'a', 2, 1, 2),
                         ('The best tournament ever', 'SamplePlayer5', 'SamplePlayer6', 0, 2, 3),
                         ('The best tournament ever', 'SamplePlayer1', 'SamplePlayer4', 1, 2, 4);

SELECT * FROM participants WHERE eventTitle = 'The best tournament ever';
SELECT * FROM participants;
INSERT INTO participants VALUES ('SamplePlayer5', 'The best tournament ever', false);
DELETE FROM participants WHERE userName='x';

SELECT * FROM fgcadb.games;
SELECT * FROM fgcadb.characters WHERE origingamename=?;
SELECT * FROM fgcadb.charactermoves WHERE characterid=?;
INSERT INTO characterMoves VALUES (1, '5M', '700', 'All', null, '11','3','17','-4',null),
                                  (1, '5H', '850/1000', 'All', 'U1', '15','11','21','-8','13-25 Strike Armor'),
                                  (1, '5S', '600x1~2', 'All', null, '10',null,'total 50','-10',null),
                                  (1, '2L', '400', 'Low', null, '8','3','13','-4',null),
                                  (1, '2M', '700', 'Low', null, '13','4','24','-12',null),
                                  (1, '2H', '850/1000', 'All', 'U1+', '15','9','23','-22','4-23 Head, 15-23 Strike Armor');