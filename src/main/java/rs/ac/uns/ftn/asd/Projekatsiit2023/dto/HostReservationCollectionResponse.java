package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import java.util.Collection;

public class HostReservationCollectionResponse {
    Collection<HostReservationResponse> hostReservationResponses;
    long totalNumberOfReservations;

    public HostReservationCollectionResponse() {
    }

    public HostReservationCollectionResponse(Collection<HostReservationResponse> hostReservationResponses, long totalNumberOfReservations) {
        this.hostReservationResponses = hostReservationResponses;
        this.totalNumberOfReservations = totalNumberOfReservations;
    }

    public Collection<HostReservationResponse> getHostReservationResponses() {
        return hostReservationResponses;
    }

    public long getTotalNumberOfReservations() {
        return totalNumberOfReservations;
    }

    public void setHostReservationResponses(Collection<HostReservationResponse> hostReservationResponses) {
        this.hostReservationResponses = hostReservationResponses;
    }

    public void setTotalNumberOfReservations(long totalNumberOfReservations) {
        this.totalNumberOfReservations = totalNumberOfReservations;
    }
}
