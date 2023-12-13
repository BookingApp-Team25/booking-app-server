package rs.ac.uns.ftn.asd.Projekatsiit2023.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;

import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
}
