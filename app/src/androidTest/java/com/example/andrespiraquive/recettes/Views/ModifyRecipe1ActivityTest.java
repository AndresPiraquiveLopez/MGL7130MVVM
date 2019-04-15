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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ModifyRecipe1ActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<> (LoginActivity.class);

    @Test
    public void modifyRecipe1ActivityTest() {
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
        appCompatEditText.perform (replaceText ("andres.piraquive"), closeSoftKeyboard ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (5000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText2 = onView (
                allOf (withId (R.id.girisEmail), withText ("andres.piraquive"),
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

        ViewInteraction appCompatEditText5 = onView (
                allOf (withId (R.id.titre),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText5.perform (scrollTo (), click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText6 = onView (
                allOf (withId (R.id.titre),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText6.perform (scrollTo (), replaceText ("Pates"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText7 = onView (
                allOf (withId (R.id.editIngredient),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText7.perform (scrollTo (), replaceText ("Pates"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText8 = onView (
                allOf (withId (R.id.editDescription),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatEditText8.perform (scrollTo (), replaceText ("Facile"), closeSoftKeyboard ());

        ViewInteraction appCompatEditText9 = onView (
                allOf (withId (R.id.editPreparation),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatEditText9.perform (scrollTo (), replaceText ("50 minutes"), closeSoftKeyboard ());

        ViewInteraction appCompatImageView2 = onView (
                allOf (withId (R.id.imageRecipe),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatImageView2.perform (scrollTo (), click ());

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

        ViewInteraction recyclerView = onView (
                allOf (withId (R.id.recycle_view_id),
                        childAtPosition (
                                withClassName (is ("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform (actionOnItemAtPosition (1, click ()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction actionMenuItemView = onView (
                allOf (withId (R.id.action_modify), withContentDescription ("Delete"),
                        childAtPosition (
                                childAtPosition (
                                        withId (R.id.action_bar),
                                        1),
                                1),
                        isDisplayed ()));
        actionMenuItemView.perform (click ());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep (7000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        ViewInteraction appCompatEditText10 = onView (
                allOf (withId (R.id.editPreparationModify), withText ("50 minutes"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatEditText10.perform (scrollTo (), replaceText ("30 minutes"));

        ViewInteraction appCompatEditText11 = onView (
                allOf (withId (R.id.editPreparationModify), withText ("30 minutes"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                4),
                        isDisplayed ()));
        appCompatEditText11.perform (closeSoftKeyboard ());

        pressBack ();

        ViewInteraction appCompatButton3 = onView (
                allOf (withId (R.id.saveRecipeButtonModify), withText ("Save"),
                        childAtPosition (
                                childAtPosition (
                                        withClassName (is ("android.widget.ScrollView")),
                                        0),
                                5)));
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
