package entity;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
public class Answer {
    private Integer id;
    private String text;
    private Integer nextQuestionId;
    private Boolean terminal;
    private String resultText;
    private GameResult result;
}