package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.HostData;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Host;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByActivationCode(UUID activationCode);

    @Query(
            "SELECT u FROM User u " +
                    "WHERE u.id = :userId"
    )
    User findUserById(@Param("userId") UUID userId);

    @Query(
            "SELECT u FROM User u " +
                    "WHERE u.username = :username"
    )
    User findGuestByUsername(@Param("username") String username);


    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findUserByUUID(@Param("userId") UUID userId);

    @Query("SELECT g FROM Guest g WHERE g.id = :guestId")
    Guest findGuestByUUID(@Param("guestId") UUID guestId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.blocked = true WHERE u.id = :userId")
    void blockUser(@Param("userId") UUID userId);
}
