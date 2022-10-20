package springapp.jokefactory.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TopicCategoryRepository extends JpaRepository<TopicCategory, TopicCategoryKey> {

    @Modifying
    @Transactional
    @Query("delete from TopicCategory tc where tc.category.id=:category_id")
    void deleteTopicCategoriesByCategory_Id(@Param("category_id") Long categoryId);


    @Modifying
    @Transactional
    @Query("delete from TopicCategory tc where tc.topic.id=:topic_id")
    void deleteTopicCategoriesByTopic_Id(@Param("topic_id") Long topicId);

    @Query(value = "SELECT tc FROM TopicCategory tc " +
            "WHERE (tc.topic.id = :topicParentId AND tc.category.id = :topicChildId) " +
            "OR (tc.category.id = :topicParentId AND tc.topic.id = :topicChildId)")
    Optional<TopicCategory> findTopicCategoryByParentIdAndChildId(@Param("topicParentId") Long topicParentId, @Param("topicChildId") Long topicChildId);
}
