package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Notification;

import java.util.Collection;

public interface NotificationService {
    public void SendNotification(Notification notification);
    Collection<NotificationResponse> getAllUserNotifications(int userId);
}
