package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserService;

import java.util.Collection;
import java.util.UUID;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyAuthority;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Host','ROLE_Admin')")
    @PutMapping(value = "/{username}")
    public ResponseEntity<MessageResponse> editAccount(@PathVariable("username") String username, @RequestBody AccountEditRequest accountEditRequest){
        MessageResponse status = userService.editAccount(username,accountEditRequest);
        return ResponseEntity.ok(status);
    }
    @GetMapping(value = "/reported")
    public ResponseEntity<Collection<UserReportResponse>> getAllReportedUsers(){
        Collection<UserReportResponse> reportedUsers = userService.getAllUserReports();
//        if(reportedUsers == null){
//            return new ResponseEntity<Collection<UserReportResponse>>(HttpStatus.NOT_FOUND);
//        }
        return ResponseEntity.ok(reportedUsers);
    }

    @GetMapping(value = "/user-by-id/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId){
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Host','ROLE_Admin')")
    @GetMapping(value="/details/{username}")
    public ResponseEntity<AccountDetailsResponse> getAccountDetails(@PathVariable("username") String username){
        AccountDetailsResponse adr=userService.getAccountDetails(username);
        if(adr==null){
            return new ResponseEntity<AccountDetailsResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(adr);
    }
    @GetMapping(value="/host-details/{hostId}")
    public ResponseEntity<AccountDetailsResponse> getHostDetails(@PathVariable("hostId") String hostId){
        AccountDetailsResponse adr=userService.getHostDetails(UUID.fromString(hostId));
        if(adr==null){
            return new ResponseEntity<AccountDetailsResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(adr);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable("username") String username){
        MessageResponse messageResponse=userService.deleteAccount(username);
        return ResponseEntity.ok(messageResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Host')")
    @PutMapping(value="/report/{username}")
    public ResponseEntity<MessageResponse> report(@PathVariable("username") String username,@RequestParam String reason){
        MessageResponse messageResponse=userService.report(username,reason);
        return  ResponseEntity.ok(messageResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Host')")
    @GetMapping(value="/check/{guestUsername}/{hostUsername}")
    public ResponseEntity<Boolean> check(@PathVariable("guestUsername") String guestUsername,@PathVariable("hostUsername") String hostUsername){
        Boolean status=userService.checkReportPermission(guestUsername,hostUsername);
        return ResponseEntity.ok(status);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @PutMapping(value = "/blockUser/{userId}")
    public ResponseEntity<Boolean> blockUser(@PathVariable("userId") String userId){
        Boolean blockedUser= userService.blockUser(UUID.fromString(userId));
//        if(blockedUser == null){
//            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
//        }
        return ResponseEntity.ok(blockedUser);
    }
}
