package com.example.andrespiraquive.recettes.Views;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.andrespiraquive.recettes.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddRecipeTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<> (LoginActivity.class);

    @Test
    public void addRecipeTest() {
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
        appCompatEditText.perform (replaceText ("andres.piraquive@yahoo.ca"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText2 = onView (
                allOf (withId (R.id.password),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                2),
                        isDisplayed ()));
        appCompatEditText2.perform (replaceText ("Asd123.."), closeSoftKeyboard ());

        ViewInteraction appCompatButton = onView (
                allOf (withId (R.id.loginButton), withText ("Login"),
                        childAtPosition (
                                childAtPosition (
                                        withId (android.R.id.content),
                                        0),
                                3),
                        isDisplayed ()));
        appCompatButton.perform (click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        openActionBarOverflowOrOptionsMenu (getInstrumentation ().getTargetContext ());

        ViewInteraction appCompatTextView = onView (
                allOf (withId (R.id.title), withText ("Add Recipe"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed ()));
        appCompatTextView.perform (click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText3 = onView (
                allOf (withId (R.id.titre),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText3.perform (scrollTo (), replaceText ("Pattes au fromage"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText4 = onView (
                allOf (withId (R.id.editIngredient),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText4.perform (scrollTo (), replaceText ("Pattes"), closeSoftKeyboard ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText5 = onView (
                allOf (withId (R.id.editIngredient), withText ("Pattes"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText5.perform (scrollTo (), replaceText ("Pattes, fromage"));

        ViewInteraction appCompatEditText6 = onView (
                allOf (withId (R.id.editIngredient), withText ("Pattes, fromage"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                2),
                        isDisplayed ()));
        appCompatEditText6.perform (closeSoftKeyboard ());

        ViewInteraction appCompatEditText7 = onView (
                allOf (withId (R.id.editDescription),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatEditText7.perform (scrollTo (), replaceText ("Melangerl"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText8 = onView (
                allOf (withId (R.id.editPreparation),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatEditText8.perform (scrollTo (), replaceText ("5 0minutes"), closeSoftKeyboard ());

        ViewInteraction appCompatImageView = onView (
                allOf (withId (R.id.imageRecipe),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatImageView.perform (scrollTo (), click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatButton2 = onView (
                allOf (withId (R.id.saveRecipeButton), withText ("Save"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatButton2.perform (scrollTo (), click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        openActionBarOverflowOrOptionsMenu (getInstrumentation ().getTargetContext ());

        ViewInteraction appCompatTextView2 = onView (
                allOf (withId (R.id.title), withText ("User Settings"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed ()));
        appCompatTextView2.perform (click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatButton3 = onView (
                allOf (withId (R.id.cikis_yap), withText ("Logout"),
                        childAtPosition (
                                allOf (withId (R.id.container),
                                        childAtPosition (
                                                withClassName (is ("android.widget.ScrollView")),
                                                0)),
                                3)));
        appCompatButton3.perform (scrollTo (), click ());
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
