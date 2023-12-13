package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.GuestRepository;

import java.util.Collection;

@Service
public class GuestServiceImplementation implements GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Collection<Guest> findAll() {
        Collection<Guest> guests = guestRepository.findAll();
        return guests;
    }
}
