# PipBoysQuest

Jeu d'aventure en Java inspire de Donjons & Dragons, adapte a l'univers Fallout.
Le joueur cree son personnage, explore un plateau et progresse avec sauvegarde en base MySQL.

## Fonctionnalites

- Ambiance Fallout (Confrerie, Terres desolees, loots, zones hostiles).
- Creation de personnage (Warrior/Wizard) et gestion via menu console.
- Plateau de jeu avec cases speciales (ennemis, objets, cases vides).
- Sauvegarde des heros et de leur progression en base MySQL.
- Catalogues BDD dedies pour ennemis et objets (`enemy_catalog`, `item_catalog`).

## Prerequis

- Java JDK 17+
- MySQL (local)
- Adminer (recommande) ou autre client SQL

## Documentation

- Schema UML : https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc locale : `PipBoysQuest/docs/javadoc/index.html`

## Installation (utilisateur)

### 1) Recuperer le projet

```zsh
git clone <url-du-repo>
cd java_game_PipBoysQuest
```

### 2) Initialiser la base MySQL avec Adminer

- Ouvrir Adminer et se connecter a votre serveur MySQL
- Executer le script `init_db.sql` a la racine du projet
- Le script cree (ou recree) la base `boutique` et les tables :
  - `character`
  - `enemy_catalog`
  - `item_catalog`

### 3) Configurer les identifiants BDD (sans modifier le code)

Le projet lit ces variables d'environnement :

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Exemple (session terminal Linux/macOS) :

```zsh
export DB_URL='jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='votre_mot_de_passe'
```

> Si ces variables ne sont pas definies, le code utilise les valeurs de dev (`dev`/`dev`).

### 4) Compiler et lancer

Depuis le dossier `PipBoysQuest/` :

```zsh
cd PipBoysQuest
javac -cp .:../lib/mysql-connector.jar @sources.txt
java -cp .:src:../lib/mysql-connector.jar serenadebird.pipboysquest.main.Main
```

## Generer la Javadoc

Depuis `PipBoysQuest/` :

```zsh
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```

Puis ouvrir `PipBoysQuest/docs/javadoc/index.html` dans un navigateur.
