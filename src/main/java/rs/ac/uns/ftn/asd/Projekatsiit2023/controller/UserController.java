package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserService;

import java.util.Collection;

@RestController
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

    @PutMapping(value = "/blockUser/{userId}")
    public ResponseEntity<Boolean> blockUser(@PathVariable("userId") int id){
        Boolean blockedUser= userService.blockUser(id);
        if(blockedUser == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(blockedUser);
    }
}
