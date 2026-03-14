package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.enemy.Enemy;
import serenadebird.pipboysquest.game.Menu;

import java.util.Random;

/**
 * Case representant une rencontre ennemie.
 *
 * <p>La rencontre est traitee une seule fois grace au flag encounterDone,
 * ce qui evite de rejouer le meme evenement si le joueur repasse dessus.</p>
 */
public class EnemyCell extends Cell {
    // Nom de secours pour les cas legacy (quand l'objet Enemy n'est pas instancie).
    private String enemyName;
    // Ennemi concret, utilise pour afficher type + niveau de danger.
    private Enemy enemy;
    // Evite de rejouer la meme rencontre a chaque passage sur la case.
    private boolean encounterDone;
    // Aleatoire local pour les tentatives de fuite.
    private final Random random = new Random();

    // Constructeur legacy base sur un simple nom.
    public EnemyCell(int position, String enemyName) {
        super(position);
        this.enemyName = enemyName;
    }

    // Constructeur recommande: stocke directement l'ennemi concret.
    public EnemyCell(int position, Enemy enemy) {
        super(position);
        this.enemy = enemy;
        // Garde aussi le nom synchronise pour compatibilite avec le mode legacy.
        this.enemyName = enemy.getName();
    }

    // Accesseur du nom ennemi (legacy ou derive de l'objet Enemy).
    public String getEnemyName() {
        return enemyName;
    }

    // Mutateur du nom ennemi (utile pour donnees legacy/tests).
    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    // Retourne l'objet ennemi complet, s'il existe.
    public Enemy getEnemy() {
        return enemy;
    }

    // Met a jour l'objet ennemi et resynchronise le nom lisible.
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        this.enemyName = enemy.getName();
    }

    @Override
    // Rend un texte descriptif selon la disponibilite d'un ennemi concret ou non.
    public String toString() {
        if (enemy != null) {
            return "Case " + getPosition() + " : zone hostile - " + enemy.getName();
        }
        return "Case " + getPosition() + " : ATTENTION ! environnement hostile" + " : ennemi (" + enemyName + ")";
    }

    /**
     * Lance l'evenement de rencontre avec l'ennemi de la case.
     */
    @Override
    public void interact(Character character, Menu menu) {
        // Si la rencontre a deja ete resolue, on n'applique plus d'effet.
        if (encounterDone) {
            System.out.println("La zone hostile de la case " + getPosition() + " est desormais securisee.");
            return;
        }

        int enemyHealth = buildEnemyHealth();
        int enemyAttack = buildEnemyAttack();
        int fleeChance = computeFleeChance(character);
        String enemyLabel = enemy != null ? enemy.getName() : enemyName;

        while (character.isAlive() && enemyHealth > 0) {
            printCombatSnapshot(character, enemyLabel, enemyHealth, enemyAttack, fleeChance);
            int action = menu.showCombatMenu(fleeChance);
            if (action == 1) {
                enemyHealth = resolveAttackTurn(character, enemyHealth);
                if (enemyHealth <= 0) {
                    encounterDone = true;
                    System.out.println("Victoire: " + enemyLabel + " est vaincu.");
                    System.out.println("La zone hostile de la case " + getPosition() + " est desormais securisee.");
                    return;
                }
                resolveEnemyCounterAttack(character, enemyAttack);
                continue;
            }

            if (tryToFlee(fleeChance)) {
                System.out.println("Fuite reussie: vous echappez a " + enemyLabel + ".");
                System.out.println("La zone hostile de la case " + getPosition() + " reste dangereuse.");
                return;
            }

            System.out.println("Fuite ratee: " + enemyLabel + " bloque votre retraite.");
            resolveEnemyCounterAttack(character, enemyAttack);
        }

        if (!character.isAlive()) {
            System.out.println("Defaite: " + character.getName() + " succombe face a " + enemyLabel + ".");
        }
    }

    /**
     * Affiche l'etat courant du combat en reprenant le format attendu dans l'UI.
     */
    private void printCombatSnapshot(Character character, String enemyLabel, int enemyHealth, int enemyAttack, int fleeChance) {
        System.out.println("Ennemi: " + enemyLabel + " | PV: " + enemyHealth + " | ATK: " + enemyAttack);
        System.out.println("Hero: " + character.getName() + " | PV: " + character.getHealthLevel() + " | Force: " + character.getAttackStrength() + " | Esquive: " + fleeChance + "%");
    }

    /**
     * Applique le tour d'attaque du joueur puis retourne les PV restants de l'ennemi.
     */
    private int resolveAttackTurn(Character character, int enemyHealth) {
        int damage = Math.max(1, character.getAttackStrength());
        int remainingHealth = Math.max(0, enemyHealth - damage);
        System.out.println("Vous attaquez et infligez " + damage + " degats.");
        return remainingHealth;
    }

    /**
     * Gere la riposte ennemie sur le joueur.
     */
    private void resolveEnemyCounterAttack(Character character, int enemyAttack) {
        if (!character.isAlive()) {
            return;
        }
        character.takeDamage(enemyAttack);
        System.out.println("L'ennemi riposte et inflige " + enemyAttack + " degats.");
        System.out.println("Vie restante: " + character.getHealthLevel());
    }

    /**
     * Tente une fuite selon la probabilite courante du personnage.
     */
    private boolean tryToFlee(int fleeChance) {
        return rollPercent() < fleeChance;
    }

    /**
     * Retourne un tirage aleatoire sur 100, isole pour faciliter les tests.
     */
    protected int rollPercent() {
        return random.nextInt(100);
    }

    /**
     * Derive les PV de l'ennemi a partir de son niveau de danger.
     */
    private int buildEnemyHealth() {
        if (enemy != null) {
            return Math.max(12, enemy.getDangerLevel() * 6);
        }
        return 18;
    }

    /**
     * Derive l'attaque de l'ennemi a partir de son niveau de danger.
     */
    private int buildEnemyAttack() {
        if (enemy != null) {
            return Math.max(4, enemy.getDangerLevel() + 3);
        }
        return 6;
    }

    /**
     * Retourne une probabilite simple de fuite/esquive selon la classe du joueur.
     */
    private int computeFleeChance(Character character) {
        if ("Wizard".equalsIgnoreCase(character.getType())) {
            return 25;
        }
        return 20;
    }
}