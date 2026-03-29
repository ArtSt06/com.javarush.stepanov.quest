package entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Question {
    private Integer id;
    private String text;
    private List<Answer> answers;
}