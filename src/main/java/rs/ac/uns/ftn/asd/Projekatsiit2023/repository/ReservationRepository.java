package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query(
            "SELECT a FROM Reservation a " +  // Add space after 'a'
                    "WHERE a.host.id = :hostId"
    )
    Page<Reservation> findAllHostReservations(
            @Param("hostId") UUID hostId,
            Pageable pageable
    );
    @Query(
            "SELECT a FROM Reservation a " +  // Add space after 'a'
                    "WHERE a.host.id = :hostId AND a.reservationStatus = 1"
    )
    Page<Reservation> findAllUnresolvedHostReservations(
            @Param("hostId") UUID hostId,
            Pageable pageable
    );


    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :accommodationId")
    List<Reservation> findAllByAccommodationId(@Param("accommodationId") UUID accommodationId);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:prefix IS NULL OR r.accommodation.name LIKE CONCAT('%', :prefix, '%')) " +
            "AND (r.guest.id = :id)")
    Page<Reservation> findByIdAndAccommodationName(@Param("prefix") String prefix,
                                                   @Param("id") UUID id,
                                                   Pageable pageable);
}