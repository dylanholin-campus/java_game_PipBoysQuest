# PipBoysQuest

Un jeu d'aventure en Java inspiré de l'univers Donjons et Dragons, adapté à la licence Fallout : créez un personnage et faites-le progresser sur un plateau.

## 🚀 Fonctionnalités

- **Système D&D à la sauce Fallout** : Incarnez un membre de la Confrérie de l'Acier (chevalier ou scribe) avec son propre équipement de départ.
- **Exploration des Terres désolées** : Parcourez un plateau de jeu de 64 cases parsemé d'embûches et de découvertes.
- **Arsenal Post-Apo** : Équipez-vous de Fusils Laser, Pistolets Gamma, Armures assistées ou Stimpaks pour survivre.
- **Rencontres hostiles** : Affrontez des Raiders et d'autres dangers typiques de la licence lors de vos déplacements.
- **Interface Pip-Boy** : Un menu console épuré simulant l'interface de votre assistant électronique.
- **Sauvegarde BDD** : Création et progression de personnages sauvegardées en temps réel dans une base MySQL.

## 🛠 Prérequis / Technos utilisées

- **Java** : JDK 17+ recommandé
- **MySQL** : Serveur de base de données local (ex: XAMPP, WAMP, ou installation native)
- **Concepts POO** : Encapsulation, Héritage, Abstraction, Polymorphisme
- **Collections** : `ArrayList`, Génériques
- **Persistance** : **JDBC** (Java Database Connectivity)
- **Documentation** : Javadoc & UML

## 🗺 Documentation

- **Schéma UML** : [Voir le diagramme](https://dylanholin-campus.github.io/java_game_PipBoysQuest/)
- **Javadoc** : Générée localement dans `PipBoysQuest/docs/javadoc/index.html`

## ⚙️ Installation et Configuration

### 1. Configuration de la Base de Données

Avant de lancer le jeu, configurez votre environnement MySQL :

1. **Assurez-vous que MySQL est actif** (via XAMPP, WAMP ou service natif).
2. **Accès via Adminer** : 
   - Lancez **Adminer** (ou PHPMyAdmin).
   - Créez une base de données nommée `boutique`.
   - Utilisez l'onglet **Requête SQL** (ou Import) d'Adminer pour exécuter le script `init_db.sql` situé à la racine du projet afin de générer la table `character`.
3. **Configurez vos accès** : modifiez les identifiants dans `src/serenadebird/pipboysquest/db/DatabaseManager.java` :
   ```java
   // Ligne 14 à 16 :
   String url = "jdbc:mysql://localhost:3306/boutique";
   String user = "VOTRE_USER";      // ex: "root"
   String password = "VOTRE_MDP";   // laissez vide "" si pas de mot de passe
   ```

### 2. Compilation et Exécution (Mode Terminal)

Depuis la racine du projet (`java_game_PipBoysQuest/`) :

```bash
# Entrer dans le dossier du projet
cd PipBoysQuest

# Compiler toutes les classes via le fichier sources.txt
javac -d bin -cp "lib/*" @sources.txt

# Lancer l'aventure
java -cp "bin:lib/*" serenadebird.pipboysquest.main.Main
```

> **Note aux développeurs** : Si vous utilisez IntelliJ IDEA, pensez à ajouter les bibliothèques du dossier `lib/` au *Project Structure > Libraries* pour éviter les erreurs de compilation JDBC.

## 📚 Documentation technique

Depuis le dossier `PipBoysQuest/` :

```bash
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
