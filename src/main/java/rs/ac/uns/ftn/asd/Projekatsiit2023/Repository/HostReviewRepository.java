package rs.ac.uns.ftn.asd.Projekatsiit2023.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.HostReview;

import java.util.Collection;
import java.util.UUID;
@Repository
public interface HostReviewRepository extends JpaRepository<HostReview, UUID> {
    Collection<HostReview> findByHostId(UUID hostId);
}
