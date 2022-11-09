package springapp.jokefactory.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT t FROM Topic t where t.name = :name")
    Optional<Topic> findTopicByName(@Param("name") String name);

    @Query(value = "SELECT t FROM Topic t WHERE t.isCategory = true")
    List<Topic> getAllCategoryTopics();

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic " +
            "ORDER BY t.name")
    List<Topic> findAllConnectedTopics(@Param("topic") Topic topic);

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic " +
            "ORDER BY t.name")
    Page<Topic> findAllConnectedTopics(@Param("topic") Topic topic, Pageable pageable);

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "LEFT JOIN t.categories categories " +
            "WHERE (parents.topicParent = :topic OR children.topicChild = :topic) AND categories.category = :category " +
            "ORDER BY t.name")
    Page<Topic> findConnectedTopicsByCategory(@Param("topic") Topic topic, @Param("category") Topic category, Pageable pageable);

    Page<Topic> findTopicByNameContaining(@RequestParam("name") String name, Pageable pageable);

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic " +
            "ORDER BY t.name")
    Page<Topic> findConnectedTopics(@Param("topic") Topic topic, Pageable pageable);
}
