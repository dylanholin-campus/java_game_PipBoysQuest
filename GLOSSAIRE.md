# Glossaire des syntaxes (Java)

Syntaxes utilisees dans mon projet

## Organisation du code

- `package` : declare le package de la classe, ce qui organise le code par dossier. Exemple : `serenadebird.pipboysquest.game` correspond au dossier `serenadebird/pipboysquest/game`.
- `import` : rend une classe d'un autre package utilisable sans son nom complet. Exemple : `import java.util.Scanner` pour lire le clavier.
- `class` : declare une classe concrete instanciable (ex. `public class Game`).
- `abstract class` : classe abstraite, sert de base et ne peut pas etre instanciee directement (ex. `Character`).
- `extends` : herite d'une classe parente pour reutiliser ses attributs/methodes (ex. `Warrior extends Character`).

## Visibilite et attributs

- `private` : accessible uniquement dans la classe, protege l'etat interne.
- `public` : accessible partout, expose les methodes/constructeurs.
- `Attributs` : variables d'instance qui decrivent un objet (ex. `private int healthLevel`).

## Constructeurs et instanciation

- `Constructeur` : methode speciale appelee a la creation de l'objet, meme nom que la classe, pas de type de retour (ex. `public Game(Menu menu)`).
- `Surcharge` : plusieurs constructeurs avec des parametres differents (ex. `OffensiveEquipment()` et `OffensiveEquipment(String, String, int)`).
- `new` : cree un nouvel objet en memoire (ex. `new Scanner(System.in)`).
- `this` : reference l'objet courant, utile pour distinguer un attribut d'un parametre (ex. `this.menu = menu`).
- `super(...)` : appelle le constructeur de la classe parente (ex. `super("Warrior", name)`).

## Methodes

- Declaration : `type nom(parametres)` (ex. `public void start()` ou `public int roll()`).
- Parametres : valeurs passees a la methode (ex. `move(int steps)`).
- `void` : indique qu'une methode ne renvoie rien (ex. `public void playTurn()`).
- `return` : renvoie une valeur et termine la methode (ex. `return endChoice == 1;`).
- `@Override` : indique qu'on redefinit une methode de la classe parente (ex. `toString()`).

## Encapsulation

- Getters/Setters : methodes pour lire/modifier un attribut `private` (ex. `getName()`, `setName(...)`).
- Avantage : controle des modifications et possibilite d'ajouter des verifications.

## Types et valeurs

- Types primitifs : `int`, `boolean` (stockent des valeurs simples).
- Types objet : `String`, `Scanner`, `Random` (stockent des references).
- Litteraux : valeurs ecrites directement dans le code (ex. `1`, `64`, `true`, `"Wizard"`).

## Structures de controle

- `if / else if / else` : execute un bloc selon une condition (ex. choix de menu).
- `while` : repete un bloc tant que la condition est vraie (ex. boucle principale du jeu).
- Comparaisons : `==`, `>=`, `<=`, `>` pour comparer des nombres.

## Tableaux

- Declaration : `String[] cells` (collection d'elements de meme type, taille fixe).
- Acces : `cells[position]` pour lire/modifier un element a un index.

## I/O console

- `System.out.print/println(...)` : affiche un message dans la console.
- `Scanner` + `nextLine()` : lit une ligne entree utilisateur.
- `Integer.parseInt(...)` : convertit une chaine en entier.

## Utilitaires

- `Random` : genere un nombre aleatoire (ex. `random.nextInt(max - min + 1) + min`).
- `toString()` : representation texte d'un objet, utile pour afficher ses infos.

## Javadoc

- `/** ... */` : bloc de commentaire reconnu par l'outil `javadoc` pour generer une documentation HTML.
- Description principale : premiere phrase du bloc, utilisee comme resume dans les pages index.
- `@param nom` : documente un parametre de methode ou constructeur.
- `@return` : documente la valeur renvoyee par une methode.
- `@throws TypeException` : documente les cas d'erreur leves par la methode.
- `@Override` + Javadoc : utile pour preciser la version specialisee d'une methode heritee.
- Generation : `javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest`.
