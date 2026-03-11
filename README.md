# PipBoysQuest

<img width="1192" height="758" alt="image" src="https://github.com/user-attachments/assets/c8470763-0c4d-419b-8b1b-0d1096afb1c1" />

> **Journal du Pip-Boy:** les Terres Desolees n'attendent personne.

Jeu d'aventure tour par tour en Java, dans une ambiance Fallout: exploration, rencontres hostiles, loot, choix tactiques et progression de personnage.

---

## Sommaire

- [Apercu](#apercu)
- [Classes jouables](#classes-jouables)
- [Demarrage rapide](#demarrage-rapide-joueur)
- [Build distribution (pour partager le jeu)](#build-distribution-pour-partager-le-jeu)
- [Mode developpeur](#mode-developpeur)
- [Structure du package dist](#structure-du-package-dist)
- [Documentation](#documentation)

---

## Apercu

Dans `PipBoysQuest`, vous incarnez un survivant et avancez case par case sur un plateau dangereux.

- Exploration de zones hostiles
- Combats
- Loot d'equipements offensifs/defensifs
- Interface texte avec fenetre Swing style pip-boy
- Sauvegarde MySQL quand la base est disponible

Si MySQL est indisponible, le jeu demarre en mode hors-ligne (sans sauvegarde/chargement BDD).

---

## Classes jouables

Pour le joueur, les archetypes sont:

- **Chevalier de l'Acier** (profil combat)
- **Scribe** (profil technique/energie)


---

## Demarrage rapide

Vous pouvez jouer à PipBoysQuest sans compiler le projet.

### Prerequis minimum

- Java 17+
- MySQL optionnel

Sur Ubuntu, vous pouvez installer Java avec:

```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

### Ubuntu / Linux

```bash
unzip PipBoysQuest-dist.zip
cd dist
./run.sh
# si besoin:
bash run.sh
```

### Windows

```bat
extraire le zip puis double cliquer sur run.bat
```

---

## Build distribution (pour partager le jeu)

Depuis la racine du repo:

### Linux / macOS

```zsh
cd PipBoysQuest
chmod +x scripts/build.sh scripts/run.sh
./scripts/build.sh
```

### Windows

```bat
cd PipBoysQuest
scripts\build.bat
```

Le build genere `PipBoysQuest/dist/`, pret a zipper et partager.

---

## Mode developpeur

### Prerequis

- Java JDK 17+
- MySQL local optionnel (utile pour sauvegarde/chargement)
- Adminer ou autre client SQL (optionnel)

### Variables d'environnement BDD (optionnel)

```zsh
export DB_URL='jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='votre_mot_de_passe'
```

Si ces variables sont absentes, des valeurs par defaut sont utilisees.
Si la connexion echoue, le jeu demarre en hors-ligne.

### Initialiser la base (si vous voulez la sauvegarde)

- Executer `init_db.sql` a la racine du projet.

### Compiler et lancer en mode source

```zsh
cd PipBoysQuest
javac -cp .:../lib/mysql-connector.jar @sources.txt
java -cp .:src:../lib/mysql-connector.jar serenadebird.pipboysquest.main.Main
```

---

## Structure du package dist

Structure attendue:

- `app/PipBoysQuest.jar`
- `run.sh`
- `run.bat`
- `lib/mysql-connector.jar`
- `image/`

---

## Documentation

- Schema UML: https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc locale: `PipBoysQuest/docs/javadoc/index.html`

Generation Javadoc:

```zsh
cd PipBoysQuest
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
