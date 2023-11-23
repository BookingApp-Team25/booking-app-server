package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.RegistrationRequest;

public interface UnregisteredUserService {
    public LoginResponse login(LoginRequest loginRequest);
    public Boolean register(RegistrationRequest registrationRequest);
}
