package com.kryptgames.health.fitwithfriends.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonArray;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.FriendsInvitation;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String senderNumber,clickAction,missionTitle;

    ArrayList<InvitePopupPojo> pariticipantsList=new ArrayList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification().getTitle().equals("Mission Invite"))
        { clickAction=remoteMessage.getNotification().getClickAction();
        JSONObject dataObject=new JSONObject(remoteMessage.getData());

        try {

            missionTitle=dataObject.getString("missionTitle");
            senderNumber=dataObject.getString("senderNumber");
            JSONArray participantsArray = new JSONArray(remoteMessage.getData().get("userinfo"));
            int length=participantsArray.length();

            for(int i=0;i<length;i++) {
                JSONObject participantsArrayJSONObject = participantsArray.getJSONObject(i);
                String image=participantsArrayJSONObject.getString("userImage");
                String name=participantsArrayJSONObject.getString("userName");
                pariticipantsList.add(new InvitePopupPojo(image,name));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String channelId = "Default";

        Intent intent = new Intent(clickAction);
        intent.putExtra("senderNumber",senderNumber);
        intent.putExtra("missionTitle",missionTitle);
        //intent.putExtra("userinfo",pariticipantsList);
        intent.putParcelableArrayListExtra("userinfo",pariticipantsList);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }
    else
    {String channelId = "Default";
        Intent intent = new Intent(clickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    }
}