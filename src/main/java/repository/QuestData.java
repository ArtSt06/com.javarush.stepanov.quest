package repository;

import entity.Question;
import lombok.Data;

import java.util.List;

import static util.Constants.DATA_FILE;

@Data
public final class QuestData {
    private static QuestData INSTANCE;

    private String intro;
    private List<Question> questions;

    private QuestData() {}

    public static String getIntro() {
        return getInstance().intro;
    }

    public static Question getQuestionById(Integer id) {
        List<Question> questions = getInstance().questions;
        if (questions == null) return null;
        return questions.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private static synchronized QuestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = QuestLoader.load(DATA_FILE);
        }
        return INSTANCE;
    }
}