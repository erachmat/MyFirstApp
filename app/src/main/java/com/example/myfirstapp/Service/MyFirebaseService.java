package com.example.myfirstapp.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Range;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.myfirstapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Random;

public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("TOKEN_UPDATE", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String url = "";
            if(remoteMessage.getData() != null) {
                url  = remoteMessage.getData().get("image");
            }

            if(!TextUtils.isEmpty(url)) {
                final String finalUrl = url;

                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(MyFirebaseService.this)
                                        .load(finalUrl)
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                showNotification(MyFirebaseService.this,
                                                        title, body, null, bitmap);
                                            }

                                            @Override
                                            public void onBitmapFailed(Drawable errorDrawable) {

                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        });
                            }
                        });
            } else {
                showNotification(MyFirebaseService.this, title, body, null, null);
            }
        }
        else  {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String url = remoteMessage.getData().get("image");

            if(!TextUtils.isEmpty(url)) {
                final String finalUrl = url;

                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(MyFirebaseService.this)
                                        .load(finalUrl)
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                showNotification(MyFirebaseService.this,
                                                        title, body, null, bitmap);
                                            }

                                            @Override
                                            public void onBitmapFailed(Drawable errorDrawable) {

                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        });
                            }
                        });
            }
            else {
                showNotification(MyFirebaseService.this, title, body, null, null);
            }
        }
    }

    private void showNotification(Context context,
                                  String title,
                                  String body, Intent pendingIntent, Bitmap bitmap) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = new Random().nextInt();
        String channelId = "mobiledev-323";
        String channelName = "mobiledev";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder;
        if(bitmap != null) {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(bitmap)
                    .setContentTitle(title)
                    .setContentText(body);
        } else {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(body);
        }

        if(pendingIntent != null) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(pendingIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
        }

        notificationManager.notify(notificationId, builder.build());
    }
}
