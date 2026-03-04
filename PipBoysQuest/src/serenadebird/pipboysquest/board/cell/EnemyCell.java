package serenadebird.pipboysquest.board.cell;

public class EnemyCell extends Cell {
    private String enemyName;

    public EnemyCell(int position, String enemyName) {
        super(position);
        this.enemyName = enemyName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    @Override
    public String toString() {
        return "Case " + getPosition() + " : ATTENTION ! environnement hostile" + " : ennemi (" + enemyName + ")";
    }
}