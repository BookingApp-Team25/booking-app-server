package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import java.util.Collection;

public class AccommodationLogCollection {
    Collection<AccommodationLog> logs;
    int totalNumberOfElements;

    public AccommodationLogCollection() {
    }

    public AccommodationLogCollection(Collection<AccommodationLog> logs, int totalNumberOfElements) {
        this.logs = logs;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public Collection<AccommodationLog> getLogs() {
        return logs;
    }

    public int getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public void setLogs(Collection<AccommodationLog> logs) {
        this.logs = logs;
    }

    public void setTotalNumberOfElements(int totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }
}
