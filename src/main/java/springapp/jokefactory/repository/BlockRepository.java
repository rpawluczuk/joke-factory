package springapp.jokefactory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springapp.jokefactory.entity.StructureBlock;

public interface BlockRepository extends JpaRepository<StructureBlock, Long> {

    @Query(value = "SELECT b FROM structure_block b where b.structure.id = ?1 order by b.position")
    List<StructureBlock> findBlocksByStructure(long structureId);
}
