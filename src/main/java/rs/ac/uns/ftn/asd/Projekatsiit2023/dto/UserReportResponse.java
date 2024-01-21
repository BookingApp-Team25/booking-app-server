package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;

import java.util.UUID;

public class UserReportResponse {
    UUID userId;
    String reason;

    public UserReportResponse() {}
    public UserReportResponse(UUID userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID id) {
        this.userId = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
