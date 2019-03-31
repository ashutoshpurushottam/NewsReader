package com.antho.newsreader.view.activities.notifications;

import androidx.core.app.NotificationCompat;

/**
 * Update active notifications from other Services/Activities
 **/
public class GlobalNotificationBuilder {
    private static NotificationCompat.Builder sGlobalNotificationCompatBuilder = null;

    // Constructor
    private GlobalNotificationBuilder() {
    }

    //
    public static void setNotificationCompatBuilderInstance(NotificationCompat.Builder builder) {
        sGlobalNotificationCompatBuilder = builder;
    }
}

