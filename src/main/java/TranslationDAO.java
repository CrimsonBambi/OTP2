import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranslationDAO {

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
                translations.add(key + " : " + text); // combine key + translation
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return translations;
    }


    // Add a new translation
    public static void addTranslation(String key, String language, String text) {
        String sql = "INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, key);
            stmt.setString(2, language);
            stmt.setString(3, text);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

