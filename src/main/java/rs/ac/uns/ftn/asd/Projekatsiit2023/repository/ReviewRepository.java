package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationReview;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.HostReview;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<AccommodationReview, UUID> {
    Collection<AccommodationReview> findByAccommodationId(UUID accommodationId);

    @Query("SELECT ar FROM AccommodationReview ar WHERE ar.reported = true")
    List<AccommodationReview> getAllReportedAccommodationReviews();

    @Query("SELECT hr FROM HostReview hr WHERE hr.reported = true")
    List<HostReview> getAllReportedHostReviews();
}
