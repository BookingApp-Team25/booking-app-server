package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Image;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository  extends JpaRepository<Image, UUID> {
    Optional<Image> findByImagePath(String imagePath);
}
