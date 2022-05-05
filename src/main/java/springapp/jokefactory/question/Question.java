package springapp.jokefactory.question;

import lombok.*;
import springapp.jokefactory.topic.Topic;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne(cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Topic category;
}
