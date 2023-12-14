package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.RegistrationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.security.jwt.JwtTokenUtil;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        UserDetails user= (UserDetails) auth.getPrincipal();
        String token = jwtTokenUtil.generateToken(user);
        LoginResponse loginResponse=new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<MessageResponse> registered(@RequestBody RegistrationRequest registrationRequest){
        MessageResponse registrationResponse = userService.createAccount(registrationRequest);
        return ResponseEntity.ok(registrationResponse);
    }
}
