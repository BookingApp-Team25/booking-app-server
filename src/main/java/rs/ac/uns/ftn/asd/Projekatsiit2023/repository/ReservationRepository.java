package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Repository
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
                    "WHERE a.host.id = :hostId"
    )
    ArrayList<Reservation> findAllHostReservations(
            @Param("hostId") UUID hostId
    );

    @Query(
            "SELECT a FROM Reservation a " +  // Add space after 'a'
                    "WHERE a.host.username = :hostUsername"
    )
    ArrayList<Reservation> findAllHostReservations(
            @Param("hostUsername") String hostUsername
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
}