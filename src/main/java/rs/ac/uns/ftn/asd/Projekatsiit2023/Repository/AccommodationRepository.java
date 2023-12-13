package rs.ac.uns.ftn.asd.Projekatsiit2023.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {
    @Query("SELECT a FROM Accommodation a " +
            "WHERE a.location.city = :city " +
            "AND :guestNumber BETWEEN a.minGuests AND a.maxGuests " +
            "AND EXISTS (SELECT 1 FROM AccommodationReservedDates ar " +
            "JOIN ar.reservations r " +
            "WHERE r.accommodationReservedDates = ar " +
            "AND ar = a.availability " + // adjust this line
            "AND r.datePeriod.startDate <= :endDate " +
            "AND r.datePeriod.endDate >= :startDate)")
    List<Accommodation> searchAccommodations(
            @Param("city") String city,
            @Param("guestNumber") int guestNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Accommodation a " +
            "WHERE a.location.city = :city " +
            "AND :guestNumber BETWEEN a.minGuests AND a.maxGuests " +
            "AND EXISTS (SELECT 1 FROM AccommodationReservedDates ar " +
            "JOIN ar.reservations r " +
            "WHERE r.accommodationReservedDates = ar " +
            "AND ar = a.availability " + // Adjusted line
            "AND r.datePeriod.startDate <= :endDate " +
            "AND r.datePeriod.endDate >= :startDate) " +
           // "AND (:amenities IS EMPTY OR :amenities IN a.amenities) " +
            "AND (:accommodationType IS NULL OR a.type = :accommodationType) " +
            "AND (:minPrice IS NULL OR a.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR a.price <= :maxPrice)")
    List<Accommodation> filterAccommodations(
            @Param("city") String city,
            @Param("guestNumber") int guestNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            //@Param("amenities") List<String> amenities,
            @Param("accommodationType") String accommodationType,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
}
