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
drop table playermatches cascade ;
CREATE TABLE PlayerMatches(
    matchID serial primary key,
    userName1 varchar(25),
    userName2 varchar(25),
    user1Score int,
    user2Score int,
    FOREIGN KEY (userName1) REFERENCES UserTable(userName),
    FOREIGN KEY (userName2) REFERENCES UserTable(userName)
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
drop table usertable cascade ;
INSERT INTO usertable VALUES ('SamplePlayer1', '1234', 2000, 'Fantic', 'user'),
                             ('SamplePlayer2', '4321', 2100, 'Baltyk', 'user'),
                             ('SamplePlayer3', '2334', 1500, 'Caltek', 'user'),
                             ('SampleModerator', '5678', 100, 'TvojaMama', 'moderator');
INSERT INTO fgcadb.usertable VALUES ('a', '12345', 130, 'test', 'user');
UPDATE usertable SET brp=2000 WHERE userName='a';
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
SELECT eventTitle, userName1, userName2, user1Score, user2Score FROM match WHERE eventTitle = 'The best tournament ever' ORDER BY arrayPosition;
INSERT INTO match VALUES ('The best tournament ever', 'SampleModerator', 'SamplePlayer2', 2, 0, 1),
                         ('The best tournament ever', 'SamplePlayer3', 'a', 1, 2, 2),
                         ('The best tournament ever', 'SamplePlayer5', 'SamplePlayer6', 0, 2, 3),
                         ('The best tournament ever', 'SamplePlayer1', 'SamplePlayer4', 2, 1, 4);

SELECT * FROM participants WHERE eventTitle = 'The best tournament ever';
SELECT * FROM participants;
INSERT INTO participants VALUES ('SamplePlayer5', 'The best tournament ever', false);
DELETE FROM participants WHERE userName='x';

SELECT * FROM fgcadb.participants WHERE eventTitle = 'The best tournament ever';

SELECT * FROM Match;