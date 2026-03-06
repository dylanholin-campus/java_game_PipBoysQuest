package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.enemy.Enemy;

public class EnemyCell extends Cell {
    private String enemyName;
    private Enemy enemy;
    private boolean encounterDone;

    public EnemyCell(int position, String enemyName) {
        super(position);
        this.enemyName = enemyName;
    }

    public EnemyCell(int position, Enemy enemy) {
        super(position);
        this.enemy = enemy;
        this.enemyName = enemy.getName();
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        this.enemyName = enemy.getName();
    }

    @Override
    public String toString() {
        if (enemy != null) {
            return "Case " + getPosition() + " : zone hostile - " + enemy;
        }
        return "Case " + getPosition() + " : ATTENTION ! environnement hostile" + " : ennemi (" + enemyName + ")";
    }

    @Override
    public void interact(Character character) {
        if (encounterDone) {
            System.out.println("Cette zone hostile est deja neutralisee.");
            return;
        }

        String enemyLabel = enemy != null ? enemy.toString() : enemyName;
        System.out.println(character.getName() + " rencontre " + enemyLabel + " !");
        System.out.println("Combat en approche (version simplifiee): interaction de combat a completer.");
        encounterDone = true;
    }
}