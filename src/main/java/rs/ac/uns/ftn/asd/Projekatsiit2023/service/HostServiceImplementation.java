package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.io.IOException;
import java.util.*;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Host;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.HostRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

@Service
public class HostServiceImplementation implements HostService{
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public AccommodationResponse sendEditRequest(int id) {
        return new AccommodationResponse();
    }
    public AccommodationMonthlyLogCollection getAnnualLog(UUID accommodationId) throws IOException {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        AccommodationMonthlyLogCollection annualLog = new AccommodationMonthlyLogCollection();
        DateManagementService dateManagementService = new DateManagementService(reservationRepository,accommodationRepository);
        List<AccommodationMonthlyLog> montlyLogs = dateManagementService.getAnnualStatistics(accommodationId);
        annualLog.setAccommodationId(accommodationId);
        annualLog.setAccommodationName(accommodation.getName());
        annualLog.setMonths(montlyLogs);
        return annualLog;
    }
    @Override
    public AccommodationLogCollection getLogsForPeriod(DatePeriod period, String hostUsername) throws IOException {

        DateManagementService dateManagementService = new DateManagementService(reservationRepository,accommodationRepository);
        Map<UUID, AccommodationLog> mapOfLogs = dateManagementService.getHostLogsForPeriod(hostUsername,period);
        AccommodationLogCollection accommodationLogCollection = new AccommodationLogCollection();
        accommodationLogCollection.setLogs(new ArrayList<>());
        for(UUID id : mapOfLogs.keySet()){
           if(mapOfLogs.get(id) != null){
               accommodationLogCollection.getLogs().add(mapOfLogs.get(id));
           }
        }
        return accommodationLogCollection;
    }


    @Override
    public Boolean editAccount(int hostId, AccountEditRequest accountEditRequest) {
        return null;
    }

    @Override
    public Collection<AccountDetailsResponse> getGuestsForHost(String hostUsername) {
        Host host = (Host) userRepository.findByUsername(hostUsername).get();
        List<Reservation> reservations=reservationRepository.findAllHostReservations(host.getId());
        Collection<AccountDetailsResponse> accountDetailsResponses=new ArrayList<AccountDetailsResponse>();
        for(Reservation reservation : reservations){
                User user = userRepository.getReferenceById(reservation.getGuest().getId());
                AccountDetailsResponse adr = new AccountDetailsResponse(user.getId().toString(), user.getUsername(), user.getFirstName(),
                        user.getLastName(), user.getAddress(), user.getPhoneNumber());
                if(!accountDetailsResponses.contains(adr)) {
                    accountDetailsResponses.add(adr);
            }
        }
        return accountDetailsResponses;
    }

    public Collection<Host> findAll() {
        Collection<Host> hosts = hostRepository.findAll();
        return hosts;
    }
}
