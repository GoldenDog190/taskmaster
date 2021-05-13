package com.GoldenDog190.taskmaster;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.AnalyticsProperties;
import com.amplifyframework.analytics.UserProfile;
import com.amplifyframework.core.Amplify;

import java.util.Date;

public class Analytics {

    private static Analytics analytics;

    public static Analytics getAnalytics() {
        if (analytics == null) {
            analytics = new Analytics();


            Amplify.Analytics.registerGlobalProperties(
                    AnalyticsProperties.builder()
                            .add("platform", "taskmaster android app")
                            .add("User has tasks", true)
                            .add("Add a task", true)
                            .add("Join a team", true)
                            .add("Cost to use task master", 0)
                            .build()
            );

            if (Amplify.Auth.getCurrentUser() != null) {
                String id = Amplify.Auth.getCurrentUser().getUserId();
                UserProfile userProfile = UserProfile.builder()
                        .email(Amplify.Auth.getCurrentUser().getUsername())
                        .build();
                Amplify.Analytics.identifyUser(id, userProfile);
            }
        }
        return analytics;
    }

    public void trackTimeSpentOnPage(Date start, Date end, String activity) {

        long duration = end.getTime() - start.getTime();
        int seconds = (int) (duration / 1000);
        Amplify.Analytics.recordEvent(
                AnalyticsEvent.builder()
                        .name("durationOnActivity")
                        .addProperty("activity", activity)
                        .addProperty("duration on this activity", seconds)
                        .build()
        );
    }

}
