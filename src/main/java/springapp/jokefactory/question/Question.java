package springapp.jokefactory.question;

import lombok.*;
import springapp.jokefactory.topic.Topic;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne
    private Topic sourceCategory;

    @ManyToOne
    private Topic targetCategory;
}
