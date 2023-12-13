package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Host;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.HostRepository;

@Service
public class HostServiceImplementation implements HostService{
    @Autowired
    private HostRepository hostRepository;

    @Override
    public AccommodationResponse sendEditRequest(int id) {
        return new AccommodationResponse();
    }

    @Override
    public Collection<AccommodationLogDataResponse> getLogsForPeriod(DatePeriod period) {
        return null;
    }


    @Override
    public Boolean editAccount(int hostId, AccountEditRequest accountEditRequest) {
        return null;
    }

    public Collection<Host> findAll() {
        Collection<Host> hosts = hostRepository.findAll();
        return hosts;
    }
}
