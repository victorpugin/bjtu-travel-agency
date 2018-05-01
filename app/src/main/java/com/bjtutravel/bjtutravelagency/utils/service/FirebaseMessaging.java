package com.bjtutravel.bjtutravelagency.utils.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.util.Log;

import com.bjtutravel.bjtutravelagency.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG,"Msg: "+remoteMessage.getNotification().getBody());

        Notification notif = new Notification.Builder(this)
                .setContentTitle("There's something new here")
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // hide the notification after its selected
        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notifManager.notify(0, notif);
    }
}
