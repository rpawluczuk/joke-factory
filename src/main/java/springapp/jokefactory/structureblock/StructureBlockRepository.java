package springapp.jokefactory.structureblock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StructureBlockRepository extends JpaRepository<StructureBlock, Long> {

    List<StructureBlock> findStructureBlocksByStructure_IdOrderByPosition(long structureId);

    @Query(value = "SELECT sb FROM structure_block sb JOIN sb.structure s JOIN s.jokes j WHERE j.id = ?1")
    List<StructureBlock> findStructureBlocksByJoke(long jokeId);
}
