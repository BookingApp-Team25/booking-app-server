package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.NotificationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Notification;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ImageRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.NotificationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImplementation implements NotificationService{
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public void SendNotification(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setInformation(notificationRequest.getInformation());
        notification.setSendTime(notificationRequest.getSendTime());
        notification.setSeen(notificationRequest.getSeen());
        Optional<User> ret = userRepository.findByUsername(notificationRequest.getReceiver());
        if(!ret.isEmpty()){
            User user = ret.get();
            notification.setReceiver(user);
        }
        notificationRepository.save(notification);
    }


    @Override
    public Collection<NotificationResponse> getAllUserNotifications(String username) {
        ArrayList<Notification> notifications = (ArrayList<Notification>) notificationRepository.findAllByReceiverUsername(username);
        ArrayList<NotificationResponse> notificationResponses = new ArrayList<>();
        for(Notification notification: notifications){
            notificationResponses.add(new NotificationResponse(notification.getId(),notification.getReceiver().getUsername(),notification.getInformation(),notification.getSendTime(),notification.getSeen()));
        }
        return notificationResponses;
    }

    @Override
    public Boolean deleteNotifications(String username) {
        notificationRepository.deleteAllByReceiverUsername(username);
        return true;
    }

    @Override
    public void seenNotification(UUID notificationId) {
        Notification notification = notificationRepository.getReferenceById(notificationId);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }
}
