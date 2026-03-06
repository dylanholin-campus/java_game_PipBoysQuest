package serenadebird.pipboysquest.db;

import serenadebird.pipboysquest.character.Character;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    public DatabaseManager() {
        this.url = envOrDefault("DB_URL", "jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC");
        this.user = envOrDefault("DB_USER", "dev");
        this.password = envOrDefault("DB_PASSWORD", "dev");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion BDD OK !");
        } catch (Exception e) {
            System.out.println("Erreur connexion : " + e.getMessage());
        }
    }

    private String envOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value.trim();
    }

    private boolean hasConnection() {
        return connection != null;
    }

    // 1. CONSIGNE : Créer la méthode getHeroes()
    public void getHeroes() {
        if (!hasConnection()) {
            System.out.println("getHeroes ignore: connexion absente.");
            return;
        }
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM `character`")) {
            System.out.println("\n=== HEROS DANS LA BDD ===");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("Id") + " | " + rs.getString("Type") + " : " + rs.getString("Name"));
            }
        } catch (Exception error) {
            System.out.println("Erreur getHeroes : " + error.getMessage());
        }
    }

    // 2. CONSIGNE : Créer la méthode createHero(Character)
    public void createHero(Character c) {
        if (!hasConnection()) {
            System.out.println("createHero ignore: connexion absente.");
            return;
        }
        try {
            String sql = "INSERT INTO `character` (Type, Name, LifePoints, Strength) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, c.getType());
            pstmt.setString(2, c.getName());
            pstmt.setInt(3, c.getHealthLevel());
            pstmt.setInt(4, c.getAttackStrength());
            pstmt.executeUpdate();

            // Récupérer l'ID généré par la BDD et le donner au personnage
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
                System.out.println("Hero " + c.getName() + " sauvegarde en BDD ! (ID: " + c.getId() + ")");
            }
        } catch (Exception error) {
            System.out.println("Erreur createHero : " + error.getMessage());
        }
    }

    // 3. CONSIGNE : Créer la méthode editHero(Character)
    public void editHero(Character c) {
        if (!hasConnection()) {
            System.out.println("editHero ignore: connexion absente.");
            return;
        }
        try {
            String sql = "UPDATE `character` SET Type = ?, Name = ?, LifePoints = ?, Strength = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getType());
            pstmt.setString(2, c.getName());
            pstmt.setInt(3, c.getHealthLevel());
            pstmt.setInt(4, c.getAttackStrength());
            pstmt.setInt(5, c.getId());
            pstmt.executeUpdate();
            System.out.println("Hero " + c.getName() + " mis a jour en BDD !");
        } catch (Exception error) {
            System.out.println("Erreur editHero : " + error.getMessage());
        }
    }

    // 4. CONSIGNE : Créer la méthode changeLifePoints(Character)
    public void changeLifePoints(Character c) {
        if (!hasConnection()) {
            System.out.println("changeLifePoints ignore: connexion absente.");
            return;
        }
        try {
            String sql = "UPDATE `character` SET LifePoints = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, c.getHealthLevel());
            pstmt.setInt(2, c.getId());
            pstmt.executeUpdate();
            System.out.println("Points de vie de " + c.getName() + " mis a jour en BDD (" + c.getHealthLevel() + " PV).");
        } catch (Exception error) {
            System.out.println("Erreur changeLifePoints : " + error.getMessage());
        }
    }

    /**
     * Ajoute un ennemi dans la table enemy_catalog.
     */
    public void createEnemy(String enemyType, String name, int dangerLevel) {
        if (!hasConnection()) {
            System.out.println("createEnemy ignore: connexion absente.");
            return;
        }
        String sql = "INSERT INTO `enemy_catalog` (EnemyType, Name, DangerLevel) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, enemyType);
            pstmt.setString(2, name);
            pstmt.setInt(3, dangerLevel);
            pstmt.executeUpdate();
            System.out.println("Ennemi ajoute: " + enemyType + " / " + name);
        } catch (Exception error) {
            System.out.println("Erreur createEnemy : " + error.getMessage());
        }
    }

    /**
     * Affiche les ennemis du catalogue.
     */
    public void getEnemies() {
        if (!hasConnection()) {
            System.out.println("getEnemies ignore: connexion absente.");
            return;
        }
        String sql = "SELECT Id, EnemyType, Name, DangerLevel FROM `enemy_catalog` ORDER BY Id";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== ENNEMIS EN BDD ===");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("Id")
                        + " | " + rs.getString("EnemyType")
                        + " | " + rs.getString("Name")
                        + " | danger +" + rs.getInt("DangerLevel"));
            }
        } catch (Exception error) {
            System.out.println("Erreur getEnemies : " + error.getMessage());
        }
    }

    /**
     * Ajoute un objet dans la table item_catalog.
     */
    public void createItem(String itemClass, String itemType, String name, int valueLevel) {
        if (!hasConnection()) {
            System.out.println("createItem ignore: connexion absente.");
            return;
        }
        String sql = "INSERT INTO `item_catalog` (ItemClass, ItemType, Name, ValueLevel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, itemClass);
            pstmt.setString(2, itemType);
            pstmt.setString(3, name);
            pstmt.setInt(4, valueLevel);
            pstmt.executeUpdate();
            System.out.println("Objet ajoute: " + itemClass + " / " + itemType + " / " + name);
        } catch (Exception error) {
            System.out.println("Erreur createItem : " + error.getMessage());
        }
    }

    /**
     * Affiche les objets du catalogue.
     */
    public void getItems() {
        if (!hasConnection()) {
            System.out.println("getItems ignore: connexion absente.");
            return;
        }
        String sql = "SELECT Id, ItemClass, ItemType, Name, ValueLevel FROM `item_catalog` ORDER BY Id";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== OBJETS EN BDD ===");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("Id")
                        + " | " + rs.getString("ItemClass")
                        + " | " + rs.getString("ItemType")
                        + " | " + rs.getString("Name")
                        + " | valeur +" + rs.getInt("ValueLevel"));
            }
        } catch (Exception error) {
            System.out.println("Erreur getItems : " + error.getMessage());
        }
    }

    /**
     * Remplit les catalogues ennemis/objets uniquement s'ils sont vides.
     */
    public void seedCatalogsIfEmpty() {
        if (!hasConnection()) {
            return;
        }
        try {
            boolean enemySeeded = false;
            boolean itemSeeded = false;

            if (isTableEmpty("enemy_catalog")) {
                seedEnemies();
                enemySeeded = true;
            }
            if (isTableEmpty("item_catalog")) {
                seedItems();
                itemSeeded = true;
            }

            if (enemySeeded || itemSeeded) {
                System.out.println("Catalogue Fallout initialise.");
            }
        } catch (Exception error) {
            System.out.println("Seed ignore : " + error.getMessage());
        }
    }

    private boolean isTableEmpty(String tableName) throws Exception {
        if (!"enemy_catalog".equals(tableName) && !"item_catalog".equals(tableName)) {
            throw new IllegalArgumentException("Table non supportee : " + tableName);
        }

        String sql = "SELECT COUNT(*) FROM `" + tableName + "`";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private void seedEnemies() throws Exception {
        String sql = "INSERT INTO `enemy_catalog` (EnemyType, Name, DangerLevel) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "Dragon");
            pstmt.setString(2, "Dragon irradie");
            pstmt.setInt(3, 8);
            pstmt.executeUpdate();

            pstmt.setString(1, "Sorcerer");
            pstmt.setString(2, "Sorcier techno-raider");
            pstmt.setInt(3, 6);
            pstmt.executeUpdate();

            pstmt.setString(1, "Goblin");
            pstmt.setString(2, "Gobelin feral");
            pstmt.setInt(3, 4);
            pstmt.executeUpdate();
        }
    }

    private void seedItems() throws Exception {
        String sql = "INSERT INTO `item_catalog` (ItemClass, ItemType, Name, ValueLevel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "OFFENSIVE");
            pstmt.setString(2, "Mace");
            pstmt.setString(3, "Massue rouillee");
            pstmt.setInt(4, 3);
            pstmt.executeUpdate();

            pstmt.setString(2, "Sword");
            pstmt.setString(3, "Epee de recuperation");
            pstmt.setInt(4, 4);
            pstmt.executeUpdate();

            pstmt.setString(2, "Lightning");
            pstmt.setString(3, "Eclair ionique");
            pstmt.setInt(4, 5);
            pstmt.executeUpdate();

            pstmt.setString(2, "Fireball");
            pstmt.setString(3, "Boule de feu plasma");
            pstmt.setInt(4, 6);
            pstmt.executeUpdate();

            pstmt.setString(1, "DEFENSIVE");
            pstmt.setString(2, "StandardPotion");
            pstmt.setString(3, "Potion standard");
            pstmt.setInt(4, 2);
            pstmt.executeUpdate();

            pstmt.setString(2, "LargePotion");
            pstmt.setString(3, "Grande potion");
            pstmt.setInt(4, 5);
            pstmt.executeUpdate();
        }
    }

    public void fermerConnexion() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception error) {
            System.out.println("Erreur fermeture connexion : " + error.getMessage());
        }
    }
}
