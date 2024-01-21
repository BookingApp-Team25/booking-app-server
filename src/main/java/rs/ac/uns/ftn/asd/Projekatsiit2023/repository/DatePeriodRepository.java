package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

// DatePeriodRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
@Repository
public interface DatePeriodRepository extends JpaRepository<DatePeriod, Long> {
    // You can add custom queries or methods if needed
}
