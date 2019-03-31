package com.antho.newsreader.view.activities.notifications.handlers;

import android.app.IntentService;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

import com.antho.newsreader.view.activities.notifications.NotificationsActivity;

/**
 * Creates a dismiss action for notifications
 **/
public class BigTextIntentService extends IntentService {
    private static final String TAG = "BigTextService";
    public static final String ACTION_DISMISS = "com.antho.newsreader/.view.activities.notifications.handlers.action.DISMISS";

    public BigTextIntentService() {
        super("BigTextIntentService");
    }

    //
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DISMISS.equals(action)) {
                handleActionDismiss();
            }
        }
    }

    // Handles action Dismiss in the provided background thread.
    private void handleActionDismiss() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NotificationsActivity.NOTIFICATION_ID);
    }
}
