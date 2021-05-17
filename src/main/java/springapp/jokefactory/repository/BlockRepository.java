package springapp.jokefactory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springapp.jokefactory.entity.Block;
import springapp.jokefactory.entity.Structure;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query
    List<Block> findBlocksByStructure(Structure structure);
}
