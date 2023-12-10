package rs.ac.uns.ftn.asd.Projekatsiit2023.Repository;

// DatePeriodRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

public interface DatePeriodRepository extends JpaRepository<DatePeriod, Long> {
    // You can add custom queries or methods if needed
}
