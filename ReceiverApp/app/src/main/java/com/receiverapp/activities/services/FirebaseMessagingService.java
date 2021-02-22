package com.receiverapp.activities.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.receiverapp.R;
import com.receiverapp.activities.activities.MainActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServic";

    // Constructor
    public FirebaseMessagingService(){

    }

    // Message received function
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        // Check if the message contains data
        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload
        if(remoteMessage.getNotification() != null){
            // Get the order ID
            String orderId = remoteMessage.getNotification().getTitle();

            // Log the information received
            Log.d(TAG, "Message notification order id: " + orderId);

            // Send the information to the sendNotification Function
            sendNotification(orderId);
        }
    }

    // On Message delete function
    @Override
    public void onDeletedMessages(){

    }

    // Send notification function
    private void sendNotification(String orderId){
        // Intent for notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        // Notification ID for notification
        int NOTIFICATION_ID = 243;
        String CHANNEL_ID = "my_channel_01";

        // Manager for notification
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "my_channel";
            String description = "This is a notification channel.";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);

        }

        // Default sound for notification
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Builder for notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(orderId)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        // Send the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

}
