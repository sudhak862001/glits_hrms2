package com.Tejaysdr.msrc.activitys;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.Tejaysdr.msrc.R;
import com.Tejaysdr.msrc.activitys.emp.EMPHomeActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
public class Utils_location {
    public static String mlat,mlong;
    public static SQLiteDatabase database;
    public static int count = 0;
    public static Cursor cursor;
    public static String random;
    public static long timeInMilliseconds;
    final public static String KEY_LOCATION_UPDATES_REQUESTED = "location-updates-requested";
    final public static String KEY_LOCATION_UPDATES_RESULT = "location-update-result";
    final public static String CHANNEL_ID = "channel_01";
    Context mcontext;

    Utils_location(Context context){
        mcontext=context;
    }

    public static void setRequestingLocationUpdates(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_LOCATION_UPDATES_REQUESTED, value)
                .apply();
    }

    public static boolean getRequestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_LOCATION_UPDATES_REQUESTED, false);
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    public static void sendNotification(Context context, String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(context,EMPHomeActivity.class);

        //notificationIntent.putExtra("from_notification", true);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(EMPHomeActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.tejayslogo)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.img_1))
                .setColor(Color.RED)
                .setContentTitle("Location update")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);

            // Channel ID
            builder.setChannelId(CHANNEL_ID);
        }

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }


    /**
     * Returns the title for reporting about a list of {@link Location} objects.
     *
     * @param context The {@link Context}.
     */
    public static String getLocationResultTitle(Context context, List<Location> locations) {

        String numLocationsReported = context.getResources().getQuantityString(
                R.plurals.num_locations_reported, locations.size(), locations.size());
        return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(new Date());
    }

    /**
     * Returns te text for reporting about a list of  {@link Location} objects.
     *
     * @param locations List of {@link Location}s.
     */
    private static String getLocationResultText(Context context, List<Location> locations) {

        if (locations.isEmpty()) {
            return context.getString(R.string.unknown_location);
        }
        StringBuilder sb = new StringBuilder();
        for (Location location : locations) {
            sb.append("(");
            sb.append(location.getLatitude());
            sb.append(", ");
            sb.append(location.getLongitude());
            sb.append(")");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void setLocationUpdatesResult(Context context, List<Location> locations) {


        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_LOCATION_UPDATES_RESULT, getLocationResultTitle(context, locations)
                        + "\n" + getLocationResultText(context, locations))
                .apply();
    }

    public  static String getLocationUpdatesResult(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_UPDATES_RESULT, "");
    }
//    public static String mlat,mlong;
//     public static SQLiteDatabase database;
//    public static int count = 0;
//     public static Cursor cursor;
//     public static String random;
//     public static long timeInMilliseconds;
//    final public static String KEY_LOCATION_UPDATES_REQUESTED = "location-updates-requested";
//    final public static String KEY_LOCATION_UPDATES_RESULT = "location-update-result";
//    final public static String CHANNEL_ID = "channel_01";
//    Context mcontext;
//
//    Utils_location(Context context){
//        mcontext=context;
//    }
//
//   public static void setRequestingLocationUpdates(Context context, boolean value) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putBoolean(KEY_LOCATION_UPDATES_REQUESTED, value)
//                .apply();
//    }
//
//    public static boolean getRequestingLocationUpdates(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getBoolean(KEY_LOCATION_UPDATES_REQUESTED, false);
//    }
//
//    /**
//     * Posts a notification in the notification bar when a transition is detected.
//     * If the user clicks the notification, control goes to the MainActivity.
//     */
//    public static void sendNotification(Context context, String notificationDetails) {
//        // Create an explicit content Intent that starts the main Activity.
//        Intent notificationIntent = new Intent(context, EMPHomeActivity.class);
//
//        //notificationIntent.putExtra("from_notification", true);
//
//        // Construct a task stack.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//
//        // Add the main Activity to the task stack as the parent.
//        stackBuilder.addParentStack(EMPHomeActivity.class);
//
//        // Push the content Intent onto the stack.
//        stackBuilder.addNextIntent(notificationIntent);
//
//        // Get a PendingIntent containing the entire back stack.
//        PendingIntent notificationPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Get a notification builder that's compatible with platform versions >= 4
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        // Define the notification settings.
//        builder.setSmallIcon(R.mipmap.ic_launcher)
//                // In a real app, you may want to use a library like Volley
//                // to decode the Bitmap.
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
//                        R.mipmap.ic_launcher))
//                .setColor(Color.RED)
//                .setContentTitle("Location update")
//                .setContentText(notificationDetails)
//                .setContentIntent(notificationPendingIntent);
//
//        // Dismiss notification once the user touches it.
//        builder.setAutoCancel(true);
//
//        // Get an instance of the Notification manager
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Android O requires a Notification Channel.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = context.getString(R.string.app_name);
//            // Create the channel for the notification
//            NotificationChannel mChannel =
//                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
//
//            // Set the Notification Channel for the Notification Manager.
//            mNotificationManager.createNotificationChannel(mChannel);
//
//            // Channel ID
//            builder.setChannelId(CHANNEL_ID);
//        }
//
//        // Issue the notification
//        mNotificationManager.notify(0, builder.build());
//    }
//
//
//    /**
//     * Returns the title for reporting about a list of {@link Location} objects.
//     *
//     * @param context The {@link Context}.
//     */
//    static String getLocationResultTitle(Context context, List<Location> locations) {
//
//        String numLocationsReported = context.getResources().getQuantityString(
//                R.plurals.num_locations_reported, locations.size(), locations.size());
//        return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(new Date());
//    }
//
//    /**
//     * Returns te text for reporting about a list of  {@link Location} objects.
//     *
//     * @param locations List of {@link Location}s.
//     */
//    private static String getLocationResultText(Context context, List<Location> locations) {
//
//        if (locations.isEmpty()) {
//            return context.getString(R.string.unknown_location);
//        }
//        StringBuilder sb = new StringBuilder();
//        for (Location location : locations) {
//            sb.append("(");
//            sb.append(location.getLatitude());
//            sb.append(", ");
//            sb.append(location.getLongitude());
//            sb.append(")");
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
//
//    public static void setLocationUpdatesResult(Context context, List<Location> locations) {
//
//
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putString(KEY_LOCATION_UPDATES_RESULT, getLocationResultTitle(context, locations)
//                        + "\n" + getLocationResultText(context, locations))
//                .apply();
//    }
//
//   public  static String getLocationUpdatesResult(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getString(KEY_LOCATION_UPDATES_RESULT, "");
//    }



}
