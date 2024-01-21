package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public interface HostService {

    public AccommodationResponse sendEditRequest(int id);
    public AccommodationLogCollection getLogsForPeriod(DatePeriod period, String hostUsername) throws IOException;
    public AccommodationMonthlyLogCollection getAnnualLog(UUID accommodationId) throws IOException;
    public Boolean editAccount(int hostId,AccountEditRequest accountEditRequest);

    public Collection<AccountDetailsResponse> getGuestsForHost(String hostUsername);
}
