package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AuthorizationService;

@RestController
@RequestMapping("api/login")
public class LoginController {
    private AuthorizationService authorizationService;
    @GetMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authorizationService.login(loginRequest);
        if(loginResponse == null){
            return new ResponseEntity<LoginResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(loginResponse);
    }
}
