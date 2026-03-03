# PipBoysQuest

Un jeu d'aventure en Java inspire de l'univers Donjons et Dragons, adapte a la licence Fallout : creez un personnage, faites-le progresser sur un plateau

## Fonctionnalites

- Creation d'un personnage avec nom et type.
- Affichage et modification des informations du personnage.
- Lancement d'une partie sur un plateau de 64 cases avec de virtuel.
- Gestion de la fin de partie et option de recommencer.
- Gestion d'une exception metier en cas de depassement du plateau (`OutOfBoardException`).
- Architecture orientee objet avec heritage, classes abstraites et equipements specialises.

## Prerequis / technos utilisees

- Java (JDK 17+ recommande)
- Terminal (zsh/bash)
- IDE Java (IntelliJ IDEA recommande)

Technos / concepts utilises :

- Java console
- Programmation orientee objet (classes, encapsulation, heritage, abstraction)
- Exceptions (`try/catch`, exception personnalisee)
- Javadoc

## Documentation

- Schema UML : https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc generee localement : `PipBoysQuest/docs/javadoc/index.html`

## Installation et execution

Depuis la racine du repo :

```sh
cd PipBoysQuest
javac @sources.txt
java -cp src serenadebird.pipboysquest.main.Main
```

## Regenerer la Javadoc

Depuis `PipBoysQuest/` :

```sh
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
