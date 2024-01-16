package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.UserReport;

import java.util.UUID;

public interface UserReportRepository extends JpaRepository<UserReport,UUID> {
}
