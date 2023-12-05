package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.RegistrationRequest;

public interface AuthorizationService {
    public LoginResponse login(LoginRequest loginRequest);
    public Boolean register(RegistrationRequest registrationRequest);
}
