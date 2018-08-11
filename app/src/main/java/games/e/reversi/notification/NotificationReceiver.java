package games.e.reversi.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Erez on 02/02/2018.
 */

public class NotificationReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification(context);
        }

        public void showNotification(Context context) {
            /*Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, MyNotificationManager.REQUEST_CODE, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setLargeIcon(BitmapFactory.decodeResource(App.Instance.getResources(), R.mipmap.icon))
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle("Reversi")
                    .setContentText("Hi! let's play and check out our latest updates");
            mBuilder.setContentIntent(pi);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(App.getUserDefault().getNotificatioWeeklyNum(), mBuilder.build());
            App.getUserDefault().setNotificationWeeklyNum(App.getUserDefault().getNotificatioWeeklyNum() + 1);
            Log.d("NOTIFICATION","push");*/
        }
}

