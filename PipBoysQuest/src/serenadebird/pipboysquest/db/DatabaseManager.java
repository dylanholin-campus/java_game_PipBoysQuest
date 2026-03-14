package serenadebird.pipboysquest.db;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;
import serenadebird.pipboysquest.equipment.defensive.PotionCatalog;
import serenadebird.pipboysquest.equipment.offensive.WeaponCatalog;

// Bibliothèque pour gérer la session de connexion avec la base de données.
import java.sql.Connection;
// Bibliothèque permettant de charger le pilote et d'établir la connexion SQL.
import java.sql.DriverManager;
// Bibliothèque pour préparer des requêtes SQL sécurisées avec des paramètres (évite les injections).
import java.sql.PreparedStatement;
// Bibliothèque pour stocker et parcourir les résultats d'une requête SELECT.
import java.sql.ResultSet;
// Bibliothèque pour exécuter des requêtes SQL simples et statiques.
import java.sql.Statement;
// Type de collection (dictionnaire) qui conserve l'ordre d'insertion des éléments.
import java.util.LinkedHashMap;
// Type d'interface pour les collections de type clé/valeur (Dictionnaire).
import java.util.Map;

/**
 * Couche d'acces aux donnees du jeu.
 *
 * <p>La classe fonctionne en mode connecte quand MySQL est disponible,
 * et degrade en mode hors-ligne quand la connexion ne peut pas etre etablie.</p>
 */
public class DatabaseManager {
    // Adresse complète de connexion vers la base de données.
    private String url;
    // Identifiant utilisé pour ouvrir la connexion.
    private String user;
    // Mot de passe utilisé pour ouvrir la connexion.
    private String password;
    // Objet JDBC central : toutes les requêtes partent de cette connexion.
    private Connection connection;

    /**
     * Initialise la connexion a partir des variables d'environnement,
     * puis bascule silencieusement en hors-ligne en cas d'echec.
     */
    public DatabaseManager() {
        // Lecture des paramètres depuis l'environnement, avec valeurs de repli.
        this.url = envOrDefault("DB_URL", "jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC");
        this.user = envOrDefault("DB_USER", "dev");
        this.password = envOrDefault("DB_PASSWORD", "dev");
        try {
            // Chargement explicite du pilote MySQL pour JDBC.
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Ouverture de la session SQL partagée par toutes les méthodes de cette classe.
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            // Si la connexion échoue, le jeu continue en mode hors ligne.
        }
    }

    /**
     * Lit une variable d'environnement ou retourne une valeur par defaut.
     */
    private String envOrDefault(String key, String fallback) {
        // Lecture brute d'une variable d'environnement système.
        String value = System.getenv(key);
        // Si elle est absente ou vide, on applique la valeur de repli.
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        // Nettoyage des espaces inutiles avant utilisation.
        return value.trim();
    }

    /**
     * @return true si une connexion SQL est active
     */
    private boolean hasConnection() {
        // Vérification minimale : la connexion existe en mémoire.
        return connection != null;
    }

    /**
     * @return true si la base est joignable depuis cette instance
     */
    public boolean isDatabaseAvailable() {
        return hasConnection();
    }

    /**
     * Affiche la liste des heros persistants.
     */
    public void getHeroes() {
        // Protection : aucune requête SQL si la base est indisponible.
        if (!hasConnection()) {
            return;
        }
        // "try (...)" ferme automatiquement Statement et ResultSet en sortie de bloc.
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM `character`")) {
            System.out.println("\n=== HEROS DANS LA BDD ===");
            // Parcours de toutes les lignes renvoyées par la requête SELECT.
            while (rs.next()) {
                // Lecture colonne par colonne et affichage lisible dans la console.
                System.out.println("- ID: " + rs.getInt("Id") + " | " + rs.getString("Type") + " : " + rs.getString("Name"));
            }
        } catch (Exception error) {
            // En jeu, on évite d'interrompre la partie en cas d'erreur de lecture.
        }
    }

    /**
     * Cree un hero en BDD et remonte son ID auto-genere dans l'objet metier.
     */
    public void createHero(Character c) {
        // Le paramètre c provient du domaine métier (Character, Warrior, Wizard).
        if (!hasConnection()) {
            return;
        }
        try {
            // Requête d'insertion d'un nouveau héros dans la table character.
            String sql = "INSERT INTO `character` (Type, Name, LifePoints, Strength, OffensiveEquipment, DefensiveEquipment) VALUES (?, ?, ?, ?, ?, ?)";
            // RETURN_GENERATED_KEYS permet de récupérer l'identifiant créé par la base.
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Association des paramètres : chaque ? reçoit une valeur Java.
            pstmt.setString(1, c.getType());
            pstmt.setString(2, c.getName());
            pstmt.setInt(3, c.getHealthLevel());
            pstmt.setInt(4, c.getAttackStrength());
            pstmt.setString(5, c.getOffensiveEquipment() != null ? c.getOffensiveEquipment().getName() : null);
            pstmt.setString(6, c.getDefensiveEquipment() != null ? c.getDefensiveEquipment().getName() : null);
            // Exécution réelle de l'insertion en base.
            pstmt.executeUpdate();

            // Récupération de l'identifiant généré puis synchronisation de l'objet métier.
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
            }
        } catch (Exception error) {
            // En cas d'erreur SQL, la session de jeu continue.
        }
    }

    /**
     * Met a jour l'etat persiste d'un hero existant.
     */
    public void editHero(Character c) {
        // Mise à jour complète d'un héros déjà existant (même identifiant).
        if (!hasConnection()) {
            return;
        }
        try {
            String sql = "UPDATE `character` SET Type = ?, Name = ?, LifePoints = ?, Strength = ?, OffensiveEquipment = ?, DefensiveEquipment = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // Les valeurs envoyées correspondent à l'état courant du personnage en mémoire.
            pstmt.setString(1, c.getType());
            pstmt.setString(2, c.getName());
            pstmt.setInt(3, c.getHealthLevel());
            pstmt.setInt(4, c.getAttackStrength());
            pstmt.setString(5, c.getOffensiveEquipment() != null ? c.getOffensiveEquipment().getName() : null);
            pstmt.setString(6, c.getDefensiveEquipment() != null ? c.getDefensiveEquipment().getName() : null);
            pstmt.setInt(7, c.getId());
            pstmt.executeUpdate();
        } catch (Exception error) {
            // En jeu, une erreur de sauvegarde ne doit pas bloquer l'utilisateur.
        }
    }

    /**
     * Met a jour uniquement les points de vie d'un hero.
     */
    public void changeLifePoints(Character c) {
        // Variante ciblée : ne modifie qu'une seule colonne (LifePoints).
        if (!hasConnection()) {
            return;
        }
        try {
            String sql = "UPDATE `character` SET LifePoints = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, c.getHealthLevel());
            pstmt.setInt(2, c.getId());
            pstmt.executeUpdate();
        } catch (Exception error) {
            // Même stratégie de tolérance aux erreurs pendant la partie.
        }
    }

    /**
     * Ajoute un ennemi dans la table enemy_catalog.
     */
    public void createEnemy(String enemyType, String name) {
        // Méthode utilitaire de remplissage du catalogue des ennemis.
        if (!hasConnection()) {
            System.out.println("createEnemy ignore: connexion absente.");
            return;
        }
        String sql = "INSERT INTO `enemy_catalog` (EnemyType, Name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Affectation des paramètres de la requête d'insertion.
            pstmt.setString(1, enemyType);
            pstmt.setString(2, name);
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
        // Affichage console du catalogue ennemi ordonné par identifiant.
        if (!hasConnection()) {
            System.out.println("getEnemies ignore: connexion absente.");
            return;
        }
        String sql = "SELECT Id, EnemyType, Name FROM `enemy_catalog` ORDER BY Id";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== ENNEMIS EN BDD ===");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("Id")
                        + " | " + rs.getString("EnemyType")
                        + " | " + rs.getString("Name"));
            }
        } catch (Exception error) {
            System.out.println("Erreur getEnemies : " + error.getMessage());
        }
    }

    /**
     * Ajoute un objet dans la table item_catalog.
     */
    public void createItem(String itemClass, String itemType, String name, int valueLevel) {
        // Insertion d'un objet de catalogue : arme ou protection.
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
        // Lecture du catalogue des objets pour vérification et affichage.
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
     * Charge le layout du plateau depuis la BDD.
     */
    public Map<Integer, String> getBoardLayoutCodes() {
        // LinkedHashMap conserve l'ordre d'insertion : utile pour un plateau séquentiel.
        Map<Integer, String> layout = new LinkedHashMap<>();
        // On ne lit le plan du plateau que si la table existe réellement.
        if (!hasConnection() || !tableExists("board_layout")) {
            return layout;
        }

        String sql = "SELECT Position, CellCode FROM `board_layout` ORDER BY Position";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Clé = position de case ; valeur = code métier de la case.
                layout.put(rs.getInt("Position"), rs.getString("CellCode"));
            }
        } catch (Exception error) {
            System.out.println("Erreur getBoardLayoutCodes : " + error.getMessage());
        }
        return layout;
    }

    /**
     * Remplace le layout du plateau par le layout genere en memoire.
     */
    public void replaceBoardLayout(Map<Integer, String> layout) {
        // Cette méthode remplace entièrement la table board_layout par une nouvelle version.
        if (!hasConnection() || !tableExists("board_layout")) {
            return;
        }
        if (layout == null || layout.isEmpty()) {
            return;
        }

        try (Statement clearStmt = connection.createStatement()) {
            // Étape 1 : vider la table avant de réinsérer le nouveau plan.
            clearStmt.executeUpdate("DELETE FROM `board_layout`");
        } catch (Exception error) {
            System.out.println("Erreur nettoyage board_layout : " + error.getMessage());
            return;
        }

        String sql = "INSERT INTO `board_layout` (Position, CellCode) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Étape 2 : insertion case par case du plan généré en mémoire.
            for (Map.Entry<Integer, String> entry : layout.entrySet()) {
                pstmt.setInt(1, entry.getKey());
                pstmt.setString(2, entry.getValue());
                pstmt.executeUpdate();
            }
        } catch (Exception error) {
            System.out.println("Erreur replaceBoardLayout : " + error.getMessage());
        }
    }

    /**
     * Remplit les catalogues ennemis/objets uniquement s'ils sont vides.
     */
    public void seedCatalogsIfEmpty() {
        // Amorçage des catalogues uniquement lors d'une base vide.
        if (!hasConnection()) {
            return;
        }
        try {
            boolean seededSomething = false;

            if (tableExists("enemy_catalog") && isTableEmpty("enemy_catalog")) {
                seedEnemies();
                seededSomething = true;
            }
            if (tableExists("item_catalog") && isTableEmpty("item_catalog")) {
                seedItems();
                seededSomething = true;
            }

            if (seededSomething) {
                // L'amorçage reste silencieux pour ne pas gêner l'expérience de jeu.
            }
        } catch (Exception error) {
            // En cas d'échec d'amorçage, le jeu conserve un comportement tolérant.
        }
    }

    private boolean isTableEmpty(String tableName) throws Exception {
        // Sécurité : si la table n'existe pas, on ne tente pas de lecture.
        if (!tableExists(tableName)) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM `" + tableName + "`";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                // COUNT(*) renvoie le nombre total de lignes de la table.
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private boolean tableExists(String tableName) {
        // Vérification d'existence via le dictionnaire système information_schema.
        String sql = "SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ? LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception error) {
            return false;
        }
    }

    private void seedEnemies() throws Exception {
        // Amorçage d'exemples d'ennemis utilisés ensuite par le plateau.
        String sql = "INSERT INTO `enemy_catalog` (EnemyType, Name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "Dragon");
            pstmt.setString(2, "Dragon Ecorcheur");
            pstmt.executeUpdate();

            pstmt.setString(1, "Sorcerer");
            pstmt.setString(2, "Sorcier techno-raider");
            pstmt.executeUpdate();

            pstmt.setString(1, "Goblin");
            pstmt.setString(2, "Gobelin feral");
            pstmt.executeUpdate();
        }
    }

    private void seedItems() throws Exception {
        // Ce contenu provient des catalogues Java WeaponCatalog et PotionCatalog.
        String sql = "INSERT INTO `item_catalog` (ItemClass, ItemType, Name, ValueLevel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "OFFENSIVE");
            pstmt.setString(2, "Weapon");
            // Pour chaque entrée d'arme, on respecte la quantité définie dans le catalogue.
            for (WeaponCatalog.Entry entry : WeaponCatalog.entries()) {
                for (int i = 0; i < entry.getQuantity(); i++) {
                    pstmt.setString(3, entry.getName());
                    pstmt.setInt(4, entry.getAttack());
                    pstmt.executeUpdate();
                }
            }

            pstmt.setString(1, "DEFENSIVE");
            // Les protections utilisent la valeur de défense issue de PotionCatalog.
            for (PotionCatalog.Entry entry : PotionCatalog.entries()) {
                pstmt.setString(2, entry.getItemType());
                pstmt.setString(3, entry.getName());
                pstmt.setInt(4, entry.getDefense());
                pstmt.executeUpdate();
            }
        }
    }

    /**
     * Ferme la connexion SQL si elle est ouverte.
     */
    public void fermerConnexion() {
        // Fermeture explicite de la session SQL en fin d'application.
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception error) {
            System.out.println("Erreur fermeture connexion : " + error.getMessage());
        }
    }

    public boolean hasHeroes() {
        // Permet à l'interface de savoir si un chargement de héros est possible.
        if (!hasConnection()) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM `character`";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception error) {
            System.out.println("Erreur hasHeroes : " + error.getMessage());
        }
        return false;
    }

    /**
     * Charge un heros par son ID.
     */
    public Character loadHeroById(int heroId) {
        // Lecture d'un héros en base puis reconstruction d'un objet métier Java.
        if (!hasConnection()) {
            System.out.println("loadHeroById ignore: connexion absente.");
            return null;
        }

        String sql = "SELECT Id, Type, Name, LifePoints, Strength FROM `character` WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, heroId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String type = rs.getString("Type");
                String name = rs.getString("Name");
                Character hero;

                if ("Warrior".equalsIgnoreCase(type)) {
                    // Lien direct avec la classe Warrior du domaine personnage.
                    hero = new Warrior(name);
                } else if ("Wizard".equalsIgnoreCase(type)) {
                    // Lien direct avec la classe Wizard du domaine personnage.
                    hero = new Wizard(name);
                } else {
                    // Type inconnu : on refuse de construire un objet incohérent.
                    return null;
                }

                // Synchronisation des attributs persistés vers l'objet Java reconstruit.
                hero.setId(rs.getInt("Id"));
                hero.setHealthLevel(rs.getInt("LifePoints"));
                hero.setAttackStrength(rs.getInt("Strength"));
                return hero;
            }
        } catch (Exception error) {
            System.out.println("Erreur loadHeroById : " + error.getMessage());
            return null;
        }
    }
}
