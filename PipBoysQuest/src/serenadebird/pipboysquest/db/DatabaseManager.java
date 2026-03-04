package serenadebird.pipboysquest.db;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    String url = "jdbc:mysql://localhost:3306/boutique?useSSL=false&serverTimezone=UTC";
    String user = "dev";
    String password = "dev";
    Connection connection;

    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✔ Connexion BDD OK !");
        } catch (Exception e) {
            System.out.println("✘ Erreur connexion : " + e.getMessage());
        }
    }

    // 1. CONSIGNE : Créer la méthode getHeroes()
    public void getHeroes() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `character`");
            System.out.println("\n=== HÉROS DANS LA BDD ===");
            while (rs.next()) {
                System.out.println("- ID: " + rs.getInt("Id") + " | " + rs.getString("Type") + " : " + rs.getString("Name"));
            }
        } catch (Exception e) {
            System.out.println("Erreur getHeroes : " + e.getMessage());
        }
    }

    // 2. CONSIGNE : Créer la méthode createHero(Character)
    public void createHero(Character c) {
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
                System.out.println("✔ Héros " + c.getName() + " sauvegardé en BDD ! (ID: " + c.getId() + ")");
            }
        } catch (Exception e) {
            System.out.println("Erreur createHero : " + e.getMessage());
        }
    }

    // 3. CONSIGNE : Créer la méthode editHero(Character)
    public void editHero(Character c) {
        try {
            String sql = "UPDATE `character` SET Type = ?, Name = ?, LifePoints = ?, Strength = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, c.getType());
            pstmt.setString(2, c.getName());
            pstmt.setInt(3, c.getHealthLevel());
            pstmt.setInt(4, c.getAttackStrength());
            pstmt.setInt(5, c.getId());

            pstmt.executeUpdate();
            System.out.println("✔ Héros " + c.getName() + " mis à jour en BDD !");
        } catch (Exception e) {
            System.out.println("Erreur editHero : " + e.getMessage());
        }
    }

    // 4. CONSIGNE : Créer la méthode changeLifePoints(Character)
    public void changeLifePoints(Character c) {
        try {
            String sql = "UPDATE `character` SET LifePoints = ? WHERE Id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, c.getHealthLevel());
            pstmt.setInt(2, c.getId());

            pstmt.executeUpdate();
            System.out.println("✔ Points de vie de " + c.getName() + " mis à jour en BDD (" + c.getHealthLevel() + " PV).");
        } catch (Exception e) {
            System.out.println("Erreur changeLifePoints : " + e.getMessage());
        }
    }

    public void fermerConnexion() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {}
    }
}
