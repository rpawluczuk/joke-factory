package springapp.jokefactory.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface TopicRelationRepository extends JpaRepository<TopicRelation, TopicRelationKey> {

    @Query(value = "SELECT trel FROM TopicRelation trel " +
            "WHERE trel.topicChild.id = :topicId OR trel.topicParent.id = :topicId")
    List<TopicRelation> findAllTopicRelationsConnectedWithTopic(@Param("topicId") Long topicId);

    @Query(value = "SELECT trel FROM TopicRelation trel " +
            "WHERE (trel.topicParent.id = :topicParentId AND trel.topicChild.id = :topicChildId) " +
            "OR (trel.topicParent.id = :topicChildId AND trel.topicChild.id = :topicParentId)")
    Optional<TopicRelation> findTopicRelationByParentIdAndChildId(@Param("topicParentId") Long topicParentId, @Param("topicChildId") Long topicChildId);
}
