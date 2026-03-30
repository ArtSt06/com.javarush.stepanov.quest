package repository;

import entity.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestDataTest {

    @Test
    void shouldLoadData() {
        assertNotNull(QuestData.getIntro());
        assertNotNull(QuestData.getQuestionById(1));
    }

    @Test
    void shouldHaveCorrectQuestStructure() {
        Question q1 = QuestData.getQuestionById(1);
        Question q4 = QuestData.getQuestionById(4);

        assertAll(
                () -> assertEquals(2, q1.getAnswers().size()),
                () -> assertEquals(2, q1.getAnswers().get(0).getNextQuestionId()),
                () -> assertEquals(3, q1.getAnswers().get(1).getNextQuestionId()),
                () -> assertEquals(2, q4.getAnswers().size()),
                () -> assertTrue(q4.getAnswers().get(0).getTerminal()),
                () -> assertTrue(q4.getAnswers().get(1).getTerminal())
        );
    }
}