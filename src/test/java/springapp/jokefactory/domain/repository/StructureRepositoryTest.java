package springapp.jokefactory.domain.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StructureRepositoryTest {

    @Test
    void getStructure() {
        StructureRepository structureRepository = new StructureRepository();

        structureRepository.createStrcture("Structure1", "Description1");
        structureRepository.createStrcture("Structure2", "Description2");

        System.out.println(structureRepository.getStructure(5).toString());
    }
}