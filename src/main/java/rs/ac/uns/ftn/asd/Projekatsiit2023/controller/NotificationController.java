package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountDetailsResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.NotificationService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping(value = "/{username}")
    public ResponseEntity<Collection<NotificationResponse>> getUserNotifications(@PathVariable("username") String username){
        Collection<NotificationResponse> notifications = notificationService.getAllUserNotifications(username);
        return ResponseEntity.ok(notifications);
    }
    @PutMapping(value = "/delete/{username}")
    public ResponseEntity<MessageResponse> deleteNotifications(@PathVariable("username") String username){
        Boolean isSuccesful = notificationService.deleteNotifications(username);
        return ResponseEntity.ok(new MessageResponse(isSuccesful,"Notifications for user deleted"));
    }
    @PostMapping(value = "/send")
    public ResponseEntity<MessageResponse> sendNotification(@RequestBody NotificationRequest notificationRequest){
        notificationService.SendNotification(notificationRequest);
        return ResponseEntity.ok(new MessageResponse(true,"notification sent"));
    }
    @PutMapping(value="/seen/{notificationId}")
    public ResponseEntity<MessageResponse> seenNotification(@PathVariable("notificationId") UUID notificationId){
        notificationService.seenNotification(notificationId);
        return ResponseEntity.ok(new MessageResponse(true,"Notification marked as seen"));
    }

}
