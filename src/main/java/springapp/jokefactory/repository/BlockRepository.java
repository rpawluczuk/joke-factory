package springapp.jokefactory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springapp.jokefactory.entity.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query(value = "SELECT b FROM Block b where b.structure.id = ?1 order by b.position")
    List<Block> findBlocksByStructure(long structureId);
}
