//package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.RegistrationRequest;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.security.jwt.JwtTokenUtil;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.service.EmailService;
//import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserService;
//
//@RestController
//@CrossOrigin(origins = "https://localhost:4200")
//@RequestMapping("api/auth")
//public class AuthenticationController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private EmailService emailService;
//    @GetMapping(value = "/test")
//    public String test(){
//        return "application is working";
//    }
//    @PostMapping(value = "/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
//        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                loginRequest.getPassword());
//        Authentication auth = authenticationManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        UserDetails user= (UserDetails) auth.getPrincipal();
//        if(userService.isBlocked(user.getUsername())){
//            LoginResponse loginResponse=new LoginResponse("");
//            return ResponseEntity.ok(loginResponse);
//        }
//        String token = jwtTokenUtil.generateToken(user);
//        LoginResponse loginResponse=new LoginResponse(token);
//        return ResponseEntity.ok(loginResponse);
//    }
//
//    @PostMapping(value = "/register")
//    public ResponseEntity<MessageResponse> register(@RequestBody RegistrationRequest registrationRequest){
//        String code = userService.createAccount(registrationRequest);
//        if(code!=null){
//            String email=registrationRequest.getUsername();
//            String subject="Activate your Zimmerman account";
//            String body="Your activation link: "+"http://localhost:4200/activation/"+code;
//            emailService.sendEmail(email,subject,body);
//            return ResponseEntity.ok(new MessageResponse(true,"Activation link is sent to your email"));
//        }
//        return ResponseEntity.ok(new MessageResponse(false,"User already exists"));
//    }
//
//    @GetMapping(value="logout")
//    public ResponseEntity<MessageResponse> logout() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!(auth instanceof AnonymousAuthenticationToken)){
//            SecurityContextHolder.clearContext();
//            return ResponseEntity.ok(new MessageResponse(true,"Successfully logged out"));
//        } else {
//            return ResponseEntity.ok(new MessageResponse(false,"User is not authenticated"));
//        }
//
//    }
//
//    @PutMapping(value="/activation/{code}")
//    public ResponseEntity<MessageResponse> activate(@PathVariable("code") String code){
//        MessageResponse messageResponse=userService.activateAccount(code);
//        return ResponseEntity.ok(messageResponse);
//    }
//}
