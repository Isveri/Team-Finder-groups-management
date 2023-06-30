package com.evi.teamfindergroupsmanagement.messaging;

import com.evi.teamfindergroupsmanagement.messaging.model.Notification;

public interface NotificationMessagingService {

    public void sendNotification(Notification notification);
}
