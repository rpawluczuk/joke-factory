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

    @Query(value = "SELECT t FROM Topic t WHERE t.isCategory = true")
    Page<Topic> getAllCategoryTopicsPage(Pageable pageable);

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
            "WHERE parents.topicParent = :topic " +
            "ORDER BY t.name")
    Page<Topic> findConnectedTopics(@Param("topic") Topic topic, Pageable pageable);

    @Query(value =
            "SELECT * FROM topic t " +
                    "WHERE id IN " +
                    "(" +
                    "SELECT id FROM topic " +
                    "LEFT JOIN topic_relation on id = topic_relation.topic_child_id " +
                    "WHERE topic_relation.topic_parent_id = :firstparentid" +
                    ") " +
                    "AND id IN " +
                    "(" +
                    "SELECT id FROM topic " +
                    "LEFT JOIN topic_relation on id = topic_relation.topic_child_id " +
                    "WHERE topic_relation.topic_parent_id = :secondparentid" +
                    ") "
            , nativeQuery = true)
    Page<Topic> findConnectedTopicsByTwoParents(@Param("firstparentid") Long firstParentId,
                                                @Param("secondparentid") Long secondParentId,
                                                Pageable pageable);

    @Query(value =
            "SELECT * FROM topic t " +
                    "LEFT JOIN topic_category on id = topic_category.topic_id " +
                    "WHERE id IN " +
                    "(" +
                    "SELECT id FROM topic " +
                    "LEFT JOIN topic_relation on id = topic_relation.topic_child_id " +
                    "WHERE topic_relation.topic_parent_id = :firstparentid" +
                    ") " +
                    "AND id IN " +
                    "(" +
                    "SELECT id FROM topic " +
                    "LEFT JOIN topic_relation on id = topic_relation.topic_child_id " +
                    "WHERE topic_relation.topic_parent_id = :secondparentid" +
                    ") AND topic_category.category_id = :categoryid"
            , nativeQuery = true)
    Page<Topic> findConnectedTopicsByTwoParentsAndCategory(@Param("firstparentid") Long firstParentId,
                                                           @Param("secondparentid") Long secondParentId,
                                                           @Param("categoryid") Long categoryId,
                                                           Pageable pageable);
}
