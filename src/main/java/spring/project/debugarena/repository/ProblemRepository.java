package spring.project.debugarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.debugarena.model.entity.ProblemEntity;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
}
