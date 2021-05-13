package com.GoldenDog190.taskmaster;

import com.amplifyframework.analytics.AnalyticsProperties;
import com.amplifyframework.analytics.UserProfile;
import com.amplifyframework.core.Amplify;

public class Analytics {

    private static Analytics analytics;

    public static Analytics getAnalytics() {
        if (analytics == null) {
            analytics = new Analytics();


            Amplify.Analytics.registerGlobalProperties(
                    AnalyticsProperties.builder()
                            .add("platform", "taskmaster android app")
                            .add("User has tasks", true)
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



}
