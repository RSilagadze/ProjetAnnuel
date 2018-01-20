SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT;
SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS;
SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION;
SET NAMES utf8mb4;

USE db_a28053_test;
DROP TABLE IF EXISTS link;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS usertype;

CREATE TABLE IF NOT EXISTS `link` (
  `Id`          INT(11) NOT NULL,
  `Url`         VARCHAR(1000)    DEFAULT NULL,
  `IdUser`      INT(11) NOT NULL DEFAULT '0',
  `DateCreated` DATE             DEFAULT NULL,
  `Name`        VARCHAR(250)     DEFAULT NULL
)
  ENGINE = MyISAM
  DEFAULT CHARSET = latin1;


INSERT INTO `link` (`Id`, `Url`, `IdUser`, `DateCreated`, `Name`) VALUES
  (1, 'test.com', 1, '2017-06-07', NULL),
  (7, 'telechargement.com', 1, '2017-06-07', NULL),
  (10, 'http://localhost:51623/Content/Page_Link', 1, '2017-06-08', NULL),
  (63, 'https://a-37.1fichier.com/c42008083', 53, '2017-07-04', 'fichier'),
  (60,
   'http://www54.uptobox.com/d/s4yvcfplhwr76xkqjvupg3tdmynpbkp6rjoqsno33ppmsdeszg3zck7sukoyalowoksinfqzylw24sa/The.Fate.of.the.Furious.2017.FRENCH.BDRip.XviD-GZR.WwW.Zone-Telechargement.Ws.avi',
   53, '2017-07-04', 'film.avi');

CREATE TABLE IF NOT EXISTS `user` (
  `Id`           INT(11)     NOT NULL,
  `IdType`       INT(11)      DEFAULT '0',
  `Nom`          VARCHAR(500) DEFAULT NULL,
  `Prenom`       VARCHAR(500) DEFAULT NULL,
  `Login`        VARCHAR(10) NOT NULL,
  `Pass`         VARCHAR(10) NOT NULL,
  `DateRegister` DATE         DEFAULT NULL
)
  ENGINE = MyISAM
  DEFAULT CHARSET = latin1;

INSERT INTO `user` (`Id`, `IdType`, `Nom`, `Prenom`, `Login`, `Pass`, `DateRegister`) VALUES
  (1, 1, 'NomTest', 'Test', '123', '123', NULL),
  (9, 2, 'Test Prem', 'Prem', '1', '1', '2017-06-08'),
  (8, 1, 'Test', 'Test', '1234', '1234', '2017-06-08'),
  (10, 2, 'Toto', 'Machin', 'toto', 'toto', '2017-06-09'),
  (52, 1, 'Toto', 'Machin', '12', '12', '2017-06-11'),
  (53, 1, 'Sirac', 'Nicolas', 'esgi', 'esgi', '2017-07-03');

CREATE TABLE IF NOT EXISTS `usertype` (
  `Id`      INT(11) NOT NULL,
  `NameStr` VARCHAR(500) DEFAULT NULL
)
  ENGINE = MyISAM
  DEFAULT CHARSET = latin1;

INSERT INTO `usertype` (`Id`, `NameStr`) VALUES
  (1, 'Free '),
  (2, 'Premium');

ALTER TABLE `link`
  ADD PRIMARY KEY (`Id`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Login` (`Login`);

ALTER TABLE `usertype`
  ADD PRIMARY KEY (`Id`);

ALTER TABLE `link`
  MODIFY `Id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 73;

ALTER TABLE `user`
  MODIFY `Id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 54;

ALTER TABLE `usertype`
  MODIFY `Id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 5;

COMMIT;

SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT;
SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS;
SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION;
