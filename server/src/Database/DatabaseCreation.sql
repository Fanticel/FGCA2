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
drop table match cascade ;
CREATE TABLE Match(
    eventTitle varchar(50),
    userName1 varchar(25),
    userName2 varchar(25),
    user1Score int,
    user2Score int,
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
SELECT * FROM usertable;
DELETE FROM event;
INSERT INTO event VALUES ('Sample event', 'Tekken69', 1500, 2500, 'going to start', 64, date('2024-05-24'), 11, 'SampleModerator');
SELECT * FROM event;

INSERT INTO participants VALUES ('SamplePlayer1', 'Sample event', true),
                                ('SamplePlayer2', 'Sample event', true),
                                ('SamplePlayer3', 'Sample event', true);
SELECT * FROM participants;

INSERT INTO match VALUES ('Sample event', 'SamplePlayer1', 'SamplePlayer2', 0, 0);

SELECT * FROM Event;
SELECT * FROM match WHERE eventTitle = 'Sample event';
SELECT * FROM participants WHERE eventTitle = 'Sample event';

SELECT * FROM fgcadb.Event;