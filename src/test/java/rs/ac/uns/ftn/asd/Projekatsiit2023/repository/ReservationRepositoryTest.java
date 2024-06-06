package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    DatePeriodRepository datePeriodRepository;
    @Test
    public void shouldFindAllReservationsForAccommodation(){
        Host host = new Host("hostTest@gmail.com","password","Ime","Prezime","Adresa","0611111111", Role.Host);
        Guest guest = new Guest("guestTest@gmail.com","password","Ime","Prezime","Adresa","0611111111", Role.Guest);
        hostRepository.save(host);
        guestRepository.save(guest);
        DatePeriod datePeriod=new DatePeriod();
        datePeriodRepository.save(datePeriod);

        Accommodation accommodation = new Accommodation("testAccommodation","Anything",new Location("Drzava","Grad","Ulica",5),new ArrayList<String>(),new ArrayList<Image>(),0,10, AccommodationType.House,20,new AccommodationPricelist(),0, AccommodationReservationPolicy.Auto);
        Accommodation accommodation1 = new Accommodation("testAccommodation1","Anything",new Location("Drzava","Grad","Ulica",5),new ArrayList<String>(),new ArrayList<Image>(),0,10, AccommodationType.House,20,new AccommodationPricelist(),0, AccommodationReservationPolicy.Auto);

        Reservation reservation1 = new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriod,10);
        Reservation reservation2 = new Reservation(guest,host,accommodation, ReservationStatus.REJECTED,datePeriod,10);
        Reservation reservation3 = new Reservation(guest,host,accommodation, ReservationStatus.WAITING_FOR_APPROVAL,datePeriod,10);
        Reservation reservation4 = new Reservation(guest,host,accommodation1, ReservationStatus.FINISHED,datePeriod,10);
        Reservation reservation5 = new Reservation(guest,host,accommodation, ReservationStatus.CANCELED,datePeriod,10);
        accommodationRepository.save(accommodation);
        accommodationRepository.save(accommodation1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.save(reservation5);


        List<Reservation> accommodationReservations = reservationRepository.findAllByAccommodationId(accommodation.getId());
        assertEquals(4,accommodationReservations.size());
    }
}
