# PipBoysQuest

<img width="1192" height="758" alt="image" src="https://github.com/user-attachments/assets/c8470763-0c4d-419b-8b1b-0d1096afb1c1" />

> **Journal du Pip-Boy:** les Terres Desolees n'attendent personne.

Jeu d'aventure tour par tour en Java, ambiance Fallout: progression sur plateau, combats interactifs, loot, decisions tactiques et progression du hero.

---

## Sommaire

- [Apercu](#apercu)
- [Guide joueur](#guide-joueur)
- [Guide developpeur](#guide-developpeur)
- [Smoke test gameplay](#smoke-test-gameplay)
- [Documentation](#documentation)

---

## Apercu

Dans `PipBoysQuest`, vous incarnez un survivant et avancez case par case sur un plateau de 64 cases.

- Rencontres ennemies avec choix d'action
- Combat tour par tour (`Attaquer` / `Tenter de fuir`)
- Loot d'armes et de potions avec confirmation `(o/n)`
- Interface texte avec fenetre Swing style Pip-Boy
- Sauvegarde MySQL quand la base est disponible

Si MySQL est indisponible, le jeu tourne en mode hors-ligne (la partie reste jouable, sans persistance BDD).

---

## Guide joueur

Vous pouvez jouer via le package `dist` sans compiler manuellement.

### Prerequis

- Java 17+ (JRE ou JDK)
- MySQL optionnel

#### Ubuntu / Linux

```bash
sudo apt update
sudo apt install openjdk-25-jdk
```

#### Windows (`run.bat`)

- Java (JDK/JRE 17+) doit etre installe
- Lien officiel Java: https://www.oracle.com/java/technologies/downloads/
- Verifier dans un terminal: `java -version`

### Lancement rapide

1. Dezippez completement le package dist
2. Aller dans le dossier `dist/`
3. Lancer le script de votre OS

#### Linux / macOS

```bash
unzip PipBoysQuest-dist.zip
cd dist
chmod u+x run.sh
./run.sh
```

#### Windows

```bat
:: Extraire le zip, ouvrir un terminal dans dist, puis lancer:
run.bat
```

---

## Guide developpeur

### Prerequis

- Java JDK 17+
- MySQL local optionnel

### Variables d'environnement BDD (optionnel)

```bash
export DB_URL='jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='votre_mot_de_passe'
```

Si ces variables sont absentes ou invalides, le jeu bascule en mode hors-ligne.

### Initialiser la base (optionnel)

Executez `init_db.sql` a la racine du projet.

### Build et run depuis les sources

#### Windows

```bat
cd PipBoysQuest
scripts\build.bat
cd dist
run.bat
```

#### Linux / macOS

```bash
cd PipBoysQuest
chmod +x scripts/build.sh scripts/run.sh
./scripts/build.sh
cd dist
./run.sh
```

> Note Windows: `build.bat` et `run.bat` tentent de detecter Java automatiquement (`JAVA_HOME`, `.jdks`, puis `PATH`).

---

## Smoke test gameplay

`GameplaySmokeTest` est un garde-fou rapide pour valider les interactions critiques sans lancer l'UI Swing.

Classe:

- `PipBoysQuest/src/serenadebird/pipboysquest/main/GameplaySmokeTest.java`

Scenarios verifies:

1. victoire combat + loot arme
2. fuite reussie (deterministe)
3. utilisation d'un Stimpack (+20)

### Execution

#### Windows (recommande)

```bat
cd PipBoysQuest
scripts\build.bat
java -cp ".\build\classes" serenadebird.pipboysquest.main.GameplaySmokeTest
```

#### Windows (si java n'est pas dans le PATH)

```bat
cd PipBoysQuest
scripts\build.bat
"C:\Users\user\.jdks\openjdk-25.0.2\bin\java.exe" -cp ".\build\classes" serenadebird.pipboysquest.main.GameplaySmokeTest
```

Si tout est correct, la sortie se termine par:

`[SMOKE TEST] OK - combat, fuite et loot (arme/potion) interactifs valides.`

---

## Documentation

- Schema UML: https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc locale: `PipBoysQuest/docs/javadoc/index.html`

Generation Javadoc:

```bash
cd PipBoysQuest
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
