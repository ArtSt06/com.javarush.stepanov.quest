package repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public final class QuestLoader {
    public static QuestData load(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = QuestLoader.class.getClassLoader()
                .getResourceAsStream(filePath)) {
            QuestData data = mapper.readValue(is, QuestData.class);
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}