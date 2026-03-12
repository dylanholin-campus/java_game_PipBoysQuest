# PipBoysQuest

<img width="1192" height="758" alt="image" src="https://github.com/user-attachments/assets/c8470763-0c4d-419b-8b1b-0d1096afb1c1" />

> **Journal du Pip-Boy:** les Terres Desolees n'attendent personne.

Jeu d'aventure tour par tour en Java, dans une ambiance Fallout: exploration, rencontres hostiles, loot, choix tactiques et progression de personnage.

---

## Sommaire

- [Apercu](#apercu)
- [Classes jouables](#classes-jouables)
- [Guide joueur](#guide-joueur)
- [Contenu du package dist](#contenu-du-package-dist)
- [Guide developpeur](#guide-developpeur)
- [Documentation](#documentation)

---

## Apercu

Dans `PipBoysQuest`, vous incarnez un survivant et avancez case par case sur un plateau dangereux.

- Exploration de zones hostiles
- Combats tour par tour
- Loot d'equipements offensifs/defensifs
- Interface texte avec fenetre Swing style Pip-Boy
- Sauvegarde MySQL quand la base est disponible

Si MySQL est indisponible, le jeu demarre en mode hors-ligne (sans sauvegarde/chargement BDD).

---

## Classes jouables

- **Chevalier de l'Acier** (profil combat)
- **Scribe** (profil technique/energie)

---

## Guide joueur

Vous pouvez jouer a `PipBoysQuest` sans compiler le projet.

### Prerequis

- Java 17+
- MySQL optionnel

#### Ubuntu / Linux

```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

#### Windows (`run.bat`)

- Java (JDK/JRE 17+) doit etre installe
- Lien officiel Java: https://www.oracle.com/java/technologies/downloads/
- Verifier dans un terminal: `java -version`

### Lancement rapide

1. Dezipper **completement** `PipBoysQuest-dist.zip`
2. Aller dans le dossier extrait `dist/`
3. Lancer le script correspondant a votre OS

#### Ubuntu / Linux

```bash
unzip PipBoysQuest-dist.zip
cd dist
./run.sh
# si besoin
bash run.sh
```

#### Windows

```bat
extraire le zip puis double cliquer sur run.bat
```

---

## Contenu du package dist

Structure attendue apres extraction:

- `app/PipBoysQuest.jar`
- `run.sh`
- `run.bat`
- `lib/mysql-connector.jar`
- `image/`

---

## Guide developpeur

### Prerequis

- Java JDK 17+
- MySQL local optionnel (utile pour sauvegarde/chargement)
- Client SQL type Adminer (optionnel)

### Variables d'environnement BDD (optionnel)

```bash
export DB_URL='jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='votre_mot_de_passe'
```

Si ces variables sont absentes, des valeurs par defaut sont utilisees.
Si la connexion echoue, le jeu demarre en hors-ligne.

### Initialiser la base (optionnel)

Executer `init_db.sql` a la racine du projet.

### Compiler et lancer depuis les sources

```bash
cd PipBoysQuest
javac -cp .:../lib/mysql-connector.jar @sources.txt
java -cp .:src:../lib/mysql-connector.jar serenadebird.pipboysquest.main.Main
```

### Generer un package dist

Depuis la racine du repo:

#### Linux / macOS

```bash
cd PipBoysQuest
chmod +x scripts/build.sh scripts/run.sh
./scripts/build.sh
```

#### Windows

```bat
cd PipBoysQuest
scripts\build.bat
```

Le build genere `PipBoysQuest/dist/`, pret a zipper et partager.

---

## Documentation

- Schema UML: https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc locale: `PipBoysQuest/docs/javadoc/index.html`

### Prerequis Javadoc (Ubuntu / Linux)

```bash
sudo apt update
sudo apt install default-jdk
```

### Generation Javadoc

```bash
cd PipBoysQuest
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
