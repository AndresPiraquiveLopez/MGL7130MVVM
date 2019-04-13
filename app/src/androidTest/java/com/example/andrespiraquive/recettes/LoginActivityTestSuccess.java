package com.example.andrespiraquive.recettes;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.andrespiraquive.recettes.Views.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTestSuccess {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<> (LoginActivity.class);

    @Test
    public void loginActivityTestSuccess() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText = onView (
                allOf (withId (R.id.girisEmail),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                1),
                        isDisplayed ()));
        appCompatEditText.perform (replaceText ("andres.piraquive@ya"), closeSoftKeyboard ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText2 = onView (
                allOf (withId (R.id.girisEmail), withText ("andres.piraquive@ya"),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                1),
                        isDisplayed ()));
        appCompatEditText2.perform (replaceText ("andres.piraquive@yahoo.ca"));

        ViewInteraction appCompatEditText3 = onView (
                allOf (withId (R.id.girisEmail), withText ("andres.piraquive@yahoo.ca"),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                1),
                        isDisplayed ()));
        appCompatEditText3.perform (closeSoftKeyboard ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText4 = onView (
                allOf (withId (R.id.password),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                2),
                        isDisplayed ()));
        appCompatEditText4.perform (replaceText ("Asd123.."), closeSoftKeyboard ());

        ViewInteraction appCompatButton = onView (
                allOf (withId (R.id.loginButton), withText ("Login"),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                3),
                        isDisplayed ()));
        appCompatButton.perform (click ());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View> () {
            @Override
            public void describeTo(Description description) {
                description.appendText ("Child at position " + position + " in parent ");
                parentMatcher.describeTo (description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent ();
                return parent instanceof ViewGroup && parentMatcher.matches (parent)
                        && view.equals (((ViewGroup) parent).getChildAt (position));
            }
        };
    }
}
