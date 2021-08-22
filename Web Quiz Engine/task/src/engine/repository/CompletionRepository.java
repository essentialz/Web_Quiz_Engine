package engine.repository;

import engine.model.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {
     Page<Completion> findAllByUserEmail(String email, Pageable paging);
}
