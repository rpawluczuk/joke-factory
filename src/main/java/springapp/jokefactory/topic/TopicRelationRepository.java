package springapp.jokefactory.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface TopicRelationRepository extends JpaRepository<TopicRelation, TopicRelationKey> {

    @Query(value = "SELECT trel FROM TopicRelation trel " +
            "WHERE trel.topicChild.id = :topicId OR trel.topicParent.id = :topicId")
    List<TopicRelation> findAllTopicRelationsConnectedWithTopic(@Param("topicId") Long topicId);
}
