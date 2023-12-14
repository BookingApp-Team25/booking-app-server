package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountDetailsResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserService;

import java.util.Collection;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyAuthority;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PutMapping(value = "/{userId}")
    public ResponseEntity<Boolean> editAccount(@PathVariable("adminId") int adminId, @RequestBody AccountEditRequest accountEditRequest){
        Boolean status = userService.editAccount(adminId,accountEditRequest);
        return ResponseEntity.ok(status);
    }
    @GetMapping(value = "/reported")
    public ResponseEntity<Collection<ReportedUserResponse>> getAllReportedUsers(){
        Collection<ReportedUserResponse> reportedUsers = userService.getAllReportedUsers();
        if(reportedUsers == null){
            return new ResponseEntity<Collection<ReportedUserResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reportedUsers);
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

    @PutMapping(value = "/blockUser/{userId}")
    public ResponseEntity<Boolean> blockUser(@PathVariable("userId") int id){
        Boolean blockedUser= userService.blockUser(id);
        if(blockedUser == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(blockedUser);
    }
}
