package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Notification;

import java.util.Collection;
@Service
public class NotificationServiceImplementation implements NotificationService{

    @Override
    public void SendNotification(Notification notification) {
        //Sending notification
    }

    @Override
    public Collection<NotificationResponse> getAllUserNotifications(int userId) {
        return null;
    }
}
