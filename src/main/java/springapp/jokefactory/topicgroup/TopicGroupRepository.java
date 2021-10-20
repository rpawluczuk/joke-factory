package springapp.jokefactory.topicgroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TopicGroupRepository extends JpaRepository<TopicGroup, Long> {


    List<TopicGroup> findTopicGroupsByJoke_Id(long jokeId);
}
