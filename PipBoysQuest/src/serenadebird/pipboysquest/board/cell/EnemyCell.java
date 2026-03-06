package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.enemy.Enemy;

public class EnemyCell extends Cell {
    private String enemyName;
    private Enemy enemy;

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
}