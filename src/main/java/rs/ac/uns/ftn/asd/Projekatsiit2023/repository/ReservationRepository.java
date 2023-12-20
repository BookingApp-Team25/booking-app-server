package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    // You can add custom query methods if needed
    @Query("SELECT r FROM Reservation r WHERE r.hostId = :hostId")
    List<Reservation> findAllByHostId(@Param("hostId") UUID hostId);

    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :accommodationId")
    List<Reservation> findAllByAccommodationId(@Param("accommodationId") UUID accommodationId);

}