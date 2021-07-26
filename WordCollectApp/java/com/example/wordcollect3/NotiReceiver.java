package com.example.wordcollect3;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class NotiReceiver extends BroadcastReceiver {


    ArrayList<String> title = MainActivity.getWord_title();
    ArrayList<String> meaning = MainActivity.getWord_meaning();


    @Override
    public void onReceive(Context context, Intent intent) {


        Intent repeating_intent = new Intent(context,MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );


        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"My Notification");
        int index = (int)(Math.random() * title.size());
        builder.setContentTitle(title.get(index));
        builder.setContentText("Meaning: "+meaning.get(index));  //
        builder.setPriority(Notification.PRIORITY_HIGH);

        builder.setSmallIcon(R.drawable.logo512px);
        builder.setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(200,builder.build());

    }
}
