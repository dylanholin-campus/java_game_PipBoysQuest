package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.board.cell.EnemyCell;
import serenadebird.pipboysquest.board.cell.ItemsCell;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.enemy.Goblin;
import serenadebird.pipboysquest.equipment.defensive.StandardPotion;
import serenadebird.pipboysquest.equipment.offensive.Weapon;
import serenadebird.pipboysquest.game.Menu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Smoke test manuel rapide pour valider le gameplay coeur sans lancer l'UI Swing.
 *
 * <p>Objectif:
 * verifier en quelques secondes que les boucles essentielles fonctionnent toujours
 * apres une modification (combat, fuite, loot arme, loot potion).</p>
 *
 * <p>Usage typique:
 * 1) compiler le projet
 * 2) executer cette classe
 * 3) verifier qu'aucune exception n'est levee et que la ligne finale [SMOKE TEST] OK apparait.</p>
 *
 * <p>Ce test ne remplace pas des tests unitaires complets; il sert de garde-fou rapide
 * pour detecter une regression evidente dans les interactions joueur.</p>
 */
public final class GameplaySmokeTest {
    private GameplaySmokeTest() {
    }

    public static void main(String[] args) {
        InputStream originalIn = System.in;
        try {
            // Scenario 1: victoire sur ennemi + equipement d'une arme.
            runVictoryAndLootScenario();
            // Scenario 2: fuite forcee reussie (test deterministe du chemin de retraite).
            runDeterministicFleeScenario();
            // Scenario 3: consommation d'une potion et verification du soin.
            runPotionScenario();
            System.out.println("[SMOKE TEST] OK - combat, fuite et loot (arme/potion) interactifs valides.");
        } finally {
            // Evite d'impacter d'autres executions: restaure l'entree standard d'origine.
            System.setIn(originalIn);
        }
    }

    private static void runVictoryAndLootScenario() {
        // Alimente System.in avec une sequence utilisateur: attaquer, attaquer, puis accepter le loot.
        String scriptedInput = String.join(System.lineSeparator(), "1", "1", "o") + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(scriptedInput.getBytes(StandardCharsets.UTF_8)));

        Menu menu = new Menu();
        Warrior hero = new Warrior("TestHero");
        EnemyCell enemyCell = new EnemyCell(7, new Goblin());
        ItemsCell itemsCell = new ItemsCell(12, new Weapon("Pistolet 9 mm classique", 3));

        enemyCell.interact(hero, menu);
        if (!hero.isAlive()) {
            // Si ce cas arrive, la logique de combat a probablement regresse.
            throw new IllegalStateException("Le hero a ete vaincu pendant le smoke test de victoire.");
        }

        itemsCell.interact(hero, menu);
        if (hero.getAttackStrength() != 18) {
            // 15 (base guerrier) + 3 (arme ramassee) = 18 attendu.
            throw new IllegalStateException("La force attendue etait 18 apres loot, obtenue=" + hero.getAttackStrength());
        }
    }

    private static void runDeterministicFleeScenario() {
        // Force une fuite immediate reussie pour tester ce chemin sans aleatoire reel.
        String scriptedInput = "2" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(scriptedInput.getBytes(StandardCharsets.UTF_8)));

        Menu menu = new Menu();
        Warrior hero = new Warrior("Runner");
        EnemyCell enemyCell = new EnemyCell(8, new Goblin()) {
            @Override
            protected int rollPercent() {
                // 0 garantit une reussite quand la chance de fuite est > 0.
                return 0;
            }
        };

        enemyCell.interact(hero, menu);
        if (hero.getHealthLevel() != 120) {
            // Une fuite reussie ne doit pas appliquer de degats.
            throw new IllegalStateException("Une fuite reussie ne devrait pas faire perdre de vie. Vie=" + hero.getHealthLevel());
        }
    }

    private static void runPotionScenario() {
        // Simule la confirmation utilisateur pour consommer une potion.
        String scriptedInput = "o" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(scriptedInput.getBytes(StandardCharsets.UTF_8)));

        Menu menu = new Menu();
        Warrior hero = new Warrior("Medic");
        // Passe volontairement sous la vie max pour rendre l'effet du soin mesurable.
        hero.takeDamage(40);

        ItemsCell potionCell = new ItemsCell(15, new StandardPotion());
        potionCell.interact(hero, menu);

        if (hero.getHealthLevel() != 100) {
            // 120 max guerrier - 40 + 20 = 100.
            throw new IllegalStateException("Le Stimpack devait soigner +20 (80 -> 100), obtenu=" + hero.getHealthLevel());
        }
    }
}
