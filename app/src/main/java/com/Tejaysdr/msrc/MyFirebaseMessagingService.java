package com.Tejaysdr.msrc;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";
    Bitmap bitmap;
    private NotificationChannel mChannel;
    public static  String ChannelID = "";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
       /* SharedPreferences pref = SplashScreenActivity.activity.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("FCMToken", s);
        editor.commit();*/
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Map data = remoteMessage.getData();
        Object PageName= ((ArrayMap) data).valueAt(0);
        Object sch_id= ((ArrayMap) data).valueAt(1);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String TrueOrFlase = remoteMessage.getData().get("title");
        //  String Imageurl=remoteMessage.getNotification().getIcon();
        String Imageurl=remoteMessage.getData().get("image");
        bitmap = getBitmapfromUrl(Imageurl);
        String click_action = remoteMessage.getNotification().getClickAction();

        //create notification
        createNotification(remoteMessage.getNotification().getBody(),click_action,PageName.toString(),remoteMessage.getNotification().getTitle(),TrueOrFlase,bitmap,remoteMessage.getNotification().getTag());
    }

    @SuppressLint("WrongConstant")
    private void createNotification(String messageBody, String clickAction, String pagename, String Title, String TrueOrFlase, Bitmap Icon, String sch_id) {
        Intent intent=null;
        intent = new Intent(clickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("MSg", clickAction);
        intent.putExtra("id", pagename);
        intent.putExtra("pushnotification", "yes");
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        /*Intent myIntent = new Intent(this, AlarmReceiver.class);
        myIntent.putExtra("ActivityName",Title);
        int id=(int) System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),id, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);*//*

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);*/

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] VIBRATE_PATTERN = {0, 500};
        //  Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.inflicted);
        NotificationCompat.Builder mNotificationBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotificationBuilder = new NotificationCompat.Builder( this)
                    .setSmallIcon(R.drawable.notifiaction1)
                    .setContentTitle(Title)
                    .setContentText(messageBody)
                    .setAutoCancel( true )
                    .setSound(notificationSoundURI)
                    .setLargeIcon(bitmap)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setLights(getResources().getColor(R.color.colorPrimary), 1000, 1000)
                    // .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentIntent(resultIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            AudioAttributes mAudioAttributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mChannel = new NotificationChannel(ChannelID, "Qwykr_Channel",
                    NotificationManager.IMPORTANCE_MAX);
            mChannel.setVibrationPattern(VIBRATE_PATTERN);
            mChannel.setImportance(Notification.DEFAULT_SOUND);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI,mAudioAttributes);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(0, mNotificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
