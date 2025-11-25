package dao;

import db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslationDAO {

    // Private constructor to prevent instantiation
    private TranslationDAO() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Logger LOGGER = Logger.getLogger(TranslationDAO.class.getName());

    public static List<String> getTranslations(String languageCode) {
        List<String> translations = new ArrayList<>();
        String sql = "SELECT Key_name, translation_text FROM translations WHERE Language_code = ? ORDER BY Key_name";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, languageCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String key = rs.getString("Key_name");
                String text = rs.getString("translation_text");
                translations.add(key + " : " + text);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching translations", e);
        }

        return translations;
    }

    public static void addTranslation(String key, String language, String text) {
        String sql = "INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            stmt.setString(2, language);
            stmt.setString(3, text);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding translation", e);
        }
    }
}