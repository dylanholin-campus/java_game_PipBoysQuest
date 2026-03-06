-- Création de la base de données (si elle n'existe pas)
CREATE DATABASE IF NOT EXISTS boutique;
USE boutique;

-- Suppression de la table si elle existe déjà (pour réinitialiser proprement)
DROP TABLE IF EXISTS `character`;
DROP TABLE IF EXISTS `enemy_catalog`;
DROP TABLE IF EXISTS `item_catalog`;
DROP TABLE IF EXISTS `board_layout`;

-- Création de la table 'character' avec les bonnes colonnes
CREATE TABLE `character` (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    LifePoints INT NOT NULL DEFAULT 100,
    Strength INT NOT NULL DEFAULT 10,
    OffensiveEquipment VARCHAR(100),
    DefensiveEquipment VARCHAR(100)
);

-- Création de la table 'enemy_catalog' pour cataloguer les ennemis
CREATE TABLE `enemy_catalog` (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    EnemyType VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    DangerLevel INT NOT NULL,
    UniverseTag VARCHAR(50) NOT NULL DEFAULT 'Fallout'
);

-- Création de la table 'item_catalog' pour cataloguer les objets lootables
CREATE TABLE `item_catalog` (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    ItemClass VARCHAR(20) NOT NULL,
    ItemType VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    ValueLevel INT NOT NULL,
    UniverseTag VARCHAR(50) NOT NULL DEFAULT 'Fallout'
);

-- Création de la table 'board_layout' pour représenter le plateau de jeu
CREATE TABLE `board_layout` (
    Position INT PRIMARY KEY,
    CellCode VARCHAR(40) NOT NULL
);

-- Insertion de VOS données (Chevalier, Scribe, Raider)
INSERT INTO `character` (Type, Name, LifePoints, Strength, OffensiveEquipment, DefensiveEquipment)
VALUES
    ('Warrior', 'Chevalier', 150, 20, 'Laser Rifle', 'Power Armor'),
    ('Wizard', 'Scribe', 100, 15, 'Pistolet Gamma', NULL);

INSERT INTO `enemy_catalog` (EnemyType, Name, DangerLevel)
VALUES
    ('Dragon', 'Dragon irradie', 8),
    ('Sorcerer', 'Sorcier techno-raider', 6),
    ('Goblin', 'Gobelin feral', 4);

INSERT INTO `item_catalog` (ItemClass, ItemType, Name, ValueLevel)
VALUES
    ('OFFENSIVE', 'Mace', 'Massue rouillee', 3),
    ('OFFENSIVE', 'Sword', 'Epee de recuperation', 4),
    ('OFFENSIVE', 'Lightning', 'Eclair ionique', 5),
    ('OFFENSIVE', 'Fireball', 'Boule de feu plasma', 6),
    ('DEFENSIVE', 'StandardPotion', 'Potion standard', 2),
    ('DEFENSIVE', 'LargePotion', 'Grande potion', 5);

-- Le layout du plateau est desormais aleatoire.
-- La table `board_layout` est remplie au demarrage du jeu (generation Java),
-- pour conserver une trace de la partie en cours.
