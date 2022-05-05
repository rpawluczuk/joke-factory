package springapp.jokefactory.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByCategory_Id(Long categoryId);
}
