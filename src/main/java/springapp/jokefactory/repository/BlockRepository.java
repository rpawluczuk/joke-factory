package springapp.jokefactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springapp.jokefactory.entity.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
