# PipBoysQuest

Un jeu d'aventure en Java inspiré de l'univers Donjons et Dragons, adapté à la licence Fallout : créez un personnage et faites-le progresser sur un plateau.

## Fonctionnalités

- **Système D&D à la sauce Fallout** : Incarnez un membre de la Confrérie de l'Acier (chevalier ou scribe) avec son propre équipement de départ.
- **Exploration des Terres désolées** : Parcourez un plateau de jeu de 64 cases parsemé d'embûches et de découvertes.
- **Arsenal Post-Apo** : Équipez-vous de Fusils Laser, Pistolets Gamma, Armures assistées ou Stimpaks pour survivre.
- **Rencontres hostiles** : Affrontez des Raiders et d'autres dangers typiques de la licence lors de vos déplacements.
- **Interface Pip-Boy** : Un menu console épuré simulant l'interface de votre assistant électronique pour gérer vos statistiques et votre progression.

## Prérequis / technos utilisées

- Java (JDK 17+ recommandé)
- Terminal (zsh/bash)
- IDE Java (IntelliJ IDEA recommandé)

Technos / concepts utilisés :

- Java 17+
- Programmation orientée objet (Encapsulation, Héritage, Abstraction, Polymorphisme)
- Collections (`ArrayList`, Generics)
- Exceptions (`try/catch`, custom exception)
- Javadoc & UML

## Documentation

- Schéma UML : https://dylanholin-campus.github.io/java_game_PipBoysQuest/
- Javadoc générée localement : `PipBoysQuest/docs/javadoc/index.html`

## Installation et exécution

Depuis la racine du repo :

```sh
cd PipBoysQuest
javac @sources.txt
java -cp src serenadebird.pipboysquest.main.Main
```

## Régénérer la Javadoc

Depuis `PipBoysQuest/` :

```sh
javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest -encoding UTF-8
```
