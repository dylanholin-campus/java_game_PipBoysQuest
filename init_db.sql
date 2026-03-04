-- Création de la base de données (si elle n'existe pas)
CREATE DATABASE IF NOT EXISTS boutique;
USE boutique;

-- Suppression de la table si elle existe déjà (pour réinitialiser proprement)
DROP TABLE IF EXISTS `character`;

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

-- Insertion de VOS données (Chevalier, Scribe, Raider)
INSERT INTO `character` (Id, Type, Name, LifePoints, Strength, OffensiveEquipment, DefensiveEquipment)
VALUES
    (1, 'Warrior', 'Chevalier', 150, 20, 'Laser Rifle', 'Power Armor'),
    (2, 'Wizard', 'Scribe', 100, 15, 'Pistolet Gamma', NULL),
    (3, 'Enemy', 'Raider', 80, 12, 'Matraque rouillée', NULL);
