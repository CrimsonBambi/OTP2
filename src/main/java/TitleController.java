import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class TitleController {
    @FXML Label titleLabel;
    @FXML Label keyLabel;
    @FXML Label translationLabel;
    @FXML ChoiceBox<String> languages;
    @FXML ListView<String> titleListView;
    @FXML TextField keyField;
    @FXML TextField translationField;
    @FXML Button addButton;

    ResourceBundle rb;
    private String currentLanguageCode = "en";

    public void initialize() {
        languages.getItems().addAll("English", "Finnish", "Japanese");

        languages.getSelectionModel().selectFirst();
        setLanguage(Locale.ENGLISH);
        refreshListView();

        languages.setOnAction(event -> {
            String selected = languages.getValue();

            if (selected.equals("English")) {
                setLanguage(Locale.ENGLISH);
                currentLanguageCode = "en";
            } else if (selected.equals("Japanese")) {
                setLanguage(Locale.JAPANESE);
                currentLanguageCode = "ja";
            } else if (selected.equals("Finnish")) {
                setLanguage(new Locale("fi", "FI"));
                currentLanguageCode = "fi";
            }
            refreshListView();
        });
    }

    private void refreshListView() {
        titleListView.getItems().clear();
        titleListView.getItems().addAll(
                TranslationDAO.getTranslations(currentLanguageCode)
        );
    }

    public void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("MessagesBundle", locale);
        updateTexts();
    }

    public void updateTexts() {
        titleLabel.setText(rb.getString("title"));
        keyField.setPromptText(rb.getString("keyField"));
        translationField.setPromptText(rb.getString("translationField"));
        keyLabel.setText(rb.getString("key"));
        translationLabel.setText(rb.getString("translation"));
        addButton.setText(rb.getString("addTranslation"));
    }

    @FXML
    public void addNewTranslation() {
        String key = keyField.getText().trim();
        String translation = translationField.getText().trim();

        if (key.isEmpty() || translation.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Missing input");
            alert.setContentText("Please enter both key and translation.");
            alert.showAndWait();
            return;
        }

        // Add translation to database
        TranslationDAO.addTranslation(key, currentLanguageCode, translation);

        // Refresh the list to show new translation
        refreshListView();

        // Clear input fields
        keyField.clear();
        translationField.clear();
    }
}
