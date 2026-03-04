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

## Tableaux et Collections

- `String[] cells` : structure de donnees de taille fixe (ex. `String[65]`).
- `ArrayList<Cell>` : collection dynamique qui peut changer de taille. Plus flexible qu'un tableau classique.
- `<Type>` : syntaxe des "Generics", permet de preciser quel type d'objets contient la collection (ex. `ArrayList<Cell>`).
- `add(objet)` : ajoute un element a la fin de la liste.
- `get(index)` : recupere l'element a la position donnee (attention, les index commencent a 0).
- `set(index, objet)` : remplace l'element existant a l'index specifie.
- `size()` : retourne le nombre d'elements dans la liste (equivalent de `.length` pour un tableau).
- `clear()` : vide completement la collection.

## Exceptions

- `Exception` : classe de base pour signaler une erreur ou un comportement inattendu pendant l'execution.
- `extends Exception` : permet de creer sa propre erreur personnalisee (ex. `OutOfBoardException`).
- `throw new ...` : declenche manuellement une exception quand une regle metier est violee.
- `throws ...` : declare dans la signature d'une methode qu'elle peut renvoyer une erreur que l'appelant devra gerer.
- `try { ... } catch (Type e) { ... }` : bloc permettant d'executer du code "a risque" et d'attraper l'erreur pour eviter que le programme ne plante (ex. gerer le depassement de plateau).
- `getMessage()` : methode pour recuperer le texte explicatif associe a l'erreur.

## Polymorphisme et types

- `instanceof` : operateur permettant de verifier si un objet appartient a une classe precise (ex. `currentCell instanceof EmptyCell`). Utile pour filtrer les affichages.
- `List` / `ArrayList` : separation entre l'interface (contrat) et l'implementation (le code reel).

## Utilitaires

- `Random` : genere un nombre aleatoire (ex. `random.nextInt(max - min + 1) + min`).
- `Dice "pipe"` : de qui renvoie toujours la meme valeur (ici `1`) pour faciliter les tests de logique de deplacement.
- `toString()` : representation texte d'un objet. Dans ce projet, chaque type de `Cell` l'utilise pour decrire son contenu au joueur.

## Javadoc

- `/** ... */` : bloc de commentaire reconnu par l'outil `javadoc` pour generer une documentation HTML.
- Description principale : premiere phrase du bloc, utilisee comme resume dans les pages index.
- `@param nom` : documente un parametre de methode ou constructeur.
- `@return` : documente la valeur renvoyee par une methode.
- `@throws TypeException` : documente les cas d'erreur leves par la methode.
- `@Override` + Javadoc : utile pour preciser la version specialisee d'une methode heritee.
- Generation : `javadoc -d docs/javadoc -sourcepath src -subpackages serenadebird.pipboysquest`.
