-- =====================================================================
--  Poseidon Capital Solutions
--  Schéma des tables + données d'amorçage (seed)
--  Noms de tables en minuscules pour correspondre aux @Table des entités.
-- =====================================================================

CREATE TABLE IF NOT EXISTS bidlist (
    BidListId INT NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    bidQuantity DOUBLE,
    askQuantity DOUBLE,
    bid DOUBLE,
    ask DOUBLE,
    benchmark VARCHAR(125),
    bidListDate TIMESTAMP NULL,
    commentary VARCHAR(125),
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    book VARCHAR(125),
    creationName VARCHAR(125),
    creationDate TIMESTAMP NULL,
    revisionName VARCHAR(125),
    revisionDate TIMESTAMP NULL,
    dealName VARCHAR(125),
    dealType VARCHAR(125),
    sourceListId VARCHAR(125),
    side VARCHAR(125),
    PRIMARY KEY (BidListId));

CREATE TABLE IF NOT EXISTS trade (
    TradeId INT NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    buyQuantity DOUBLE,
    sellQuantity DOUBLE,
    buyPrice DOUBLE,
    sellPrice DOUBLE,
    tradeDate TIMESTAMP NULL,
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    benchmark VARCHAR(125),
    book VARCHAR(125),
    creationName VARCHAR(125),
    creationDate TIMESTAMP NULL,
    revisionName VARCHAR(125),
    revisionDate TIMESTAMP NULL,
    dealName VARCHAR(125),
    dealType VARCHAR(125),
    sourceListId VARCHAR(125),
    side VARCHAR(125),
    PRIMARY KEY (TradeId));

CREATE TABLE IF NOT EXISTS curvepoint (
    Id INT NOT NULL AUTO_INCREMENT,
    CurveId INT,
    asOfDate TIMESTAMP NULL,
    term DOUBLE,
    `value` DOUBLE,
    creationDate TIMESTAMP NULL,
    PRIMARY KEY (Id));

CREATE TABLE IF NOT EXISTS rating (
    Id INT NOT NULL AUTO_INCREMENT,
    moodysRating VARCHAR(125),
    sandPRating VARCHAR(125),
    fitchRating VARCHAR(125),
    orderNumber INT,
    PRIMARY KEY (Id));

CREATE TABLE IF NOT EXISTS rulename (
    Id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(125),
    description VARCHAR(125),
    json VARCHAR(125),
    template VARCHAR(512),
    sqlStr VARCHAR(125),
    sqlPart VARCHAR(125),
    PRIMARY KEY (Id));

CREATE TABLE IF NOT EXISTS users (
    Id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(125) NOT NULL,
    password VARCHAR(125) NOT NULL,
    fullname VARCHAR(125),
    role VARCHAR(125),
    PRIMARY KEY (Id),
    UNIQUE KEY uk_users_username (username));

-- ---------------------------------------------------------------------
--  Comptes par défaut (hash BCrypt régénérés, coût 10)
--  admin / Admin123! (rôle ADMIN)
--  user  / User123! (rôle USER)
--  INSERT IGNORE + clé unique sur username => script ré-exécutable.
-- ---------------------------------------------------------------------
INSERT IGNORE INTO users (username, password, fullname, role) VALUES ('admin', '$2b$10$sXA5Ol4bbwV/MxGHhfvuYuieDVjgO.DxwboeLuqMgZc4vBoNsy4VS', 'Administrator', 'ADMIN');
INSERT IGNORE INTO users (username, password, fullname, role) VALUES ('user', '$2b$10$6apgKly08ZSlBk.tNayVg.00w3bPj/qFdZm9Z.h8oFTafFZtCVRiW', 'User', 'USER');