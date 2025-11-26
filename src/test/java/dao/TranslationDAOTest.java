package dao;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TranslationDAOTest {

    private static final String TEST_LANG = "en";
    private static final String TEST_KEY = "unittest_key";
    private static final String TEST_TEXT = "Unit Test Translation";

    @BeforeEach
    void cleanBefore() {
        // to avoid duplicates
        try {
            TranslationDAO.getTranslations(TEST_LANG).removeIf(s -> s.contains(TEST_KEY));
        } catch (Exception ignored) {
            // not important in testing
        }
    }

    @Test
    void testAddAndGetTranslations() {
        // new translation
        TranslationDAO.addTranslation(TEST_KEY, TEST_LANG, TEST_TEXT);

        // get
        List<String> translations = TranslationDAO.getTranslations(TEST_LANG);

        // verify
        boolean exists = translations.stream().anyMatch(s -> s.contains(TEST_KEY) && s.contains(TEST_TEXT));
        assertTrue(exists, "The translation should exist in the list");
    }
}
