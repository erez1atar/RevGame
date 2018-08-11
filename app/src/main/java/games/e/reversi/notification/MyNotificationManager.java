package games.e.reversi.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import games.e.reversi.utility.App;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Erez on 02/02/2018.
 */

public class MyNotificationManager {

    public static int REQUEST_CODE = 1;

    public static void scheduleWeeklyNotification() {
        Intent intent = new Intent(App.Instance, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(App.Instance, REQUEST_CODE, intent, 0);
        AlarmManager am = (AlarmManager) App.Instance.getSystemService(ALARM_SERVICE);
        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY*7, pendingIntent);
    }

}
