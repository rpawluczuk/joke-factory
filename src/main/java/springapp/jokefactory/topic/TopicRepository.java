package springapp.jokefactory.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT t FROM Topic t where t.name = :name")
    Optional<Topic> findTopicByName(@Param("name") String name);

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic " +
            "ORDER BY t.name")
    List<Topic> findAllConnectedTopics(@Param("topic") Topic topic);

    Page<Topic> findTopicByNameContaining(@RequestParam("name") String name, Pageable pageable);

    @Query(value = "SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic")
    Page<Topic> findConnectedTopics(@Param("topic") Topic topic, Pageable pageable);
}
