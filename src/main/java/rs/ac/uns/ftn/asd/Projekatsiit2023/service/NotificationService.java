package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Notification;

import java.util.Collection;
import java.util.UUID;

public interface NotificationService {
    public void SendNotification(NotificationRequest notificationRequest);
    Collection<NotificationResponse> getAllUserNotifications(String username);
    public Boolean deleteNotifications(String username);

    public void seenNotification(UUID notificationId);
}
