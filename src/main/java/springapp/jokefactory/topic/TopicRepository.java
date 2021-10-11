package springapp.jokefactory.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT t FROM Topic t where t.name = :name")
    Optional<Topic> findTopicByName(@Param("name") String name);

    @Query(value = "SELECT t FROM Topic t " +
            "LEFT JOIN t.parents parents " +
            "LEFT JOIN t.children children " +
            "WHERE parents.topicParent = :topic OR children.topicChild = :topic")
    Set<Topic> findAllConnectedTopics(@Param("topic") Topic topic);
}
