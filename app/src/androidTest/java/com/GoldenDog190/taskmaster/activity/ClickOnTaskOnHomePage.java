package com.GoldenDog190.taskmaster.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.GoldenDog190.taskmaster.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ClickOnTaskOnHomePage {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnTaskOnHomePage() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.taskRecycleView),
                        childAtPosition(
                                withId(R.id.mainConstraintLayout),
                                9)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.taskRecycleView),
                        childAtPosition(
                                withId(R.id.mainConstraintLayout),
                                9)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.goHomeButton), withText("home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textViewTaskItem), withText("Task 1 Walk the dog today"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.taskRecycleView)))),
                        isDisplayed()));
        textView.check(matches(withText("Task 1 Walk the dog today")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textViewTaskItem), withText("Task 1 Walk the dog today"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.taskRecycleView)))),
                        isDisplayed()));
        textView2.check(matches(withText("Task 1 Walk the dog today")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
