package com.GoldenDog190.taskmaster;

import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushListenerService extends FirebaseMessagingService {
    public static String TAG = "GoldenDog190.PushListenerService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "onMessageReceived: " +  remoteMessage.toString());

//        Toast toast = Toast.makeText(getApplicationContext(), remoteMessage.getData().toString(), Toast.LENGTH_LONG);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationCompat.CATEGORY_ERROR)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(remoteMessage.getFrom())
//                .setContentText(remoteMessage.getSentTime() + "")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1, builder.build());


    }

}
