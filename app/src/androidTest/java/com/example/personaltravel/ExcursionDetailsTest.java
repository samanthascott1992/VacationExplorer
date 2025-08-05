package com.example.personaltravel;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.core.IsEqual.equalTo;

import android.content.Intent;
import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.personaltravel.R;
import com.example.personaltravel.UI.ExcursionDetails;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExcursionDetailsTest {

    @Test
    public void excursionDateBeforeVacationStart_showsValidationError() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ExcursionDetails.class);
        intent.putExtra("vacationID", 1);
        intent.putExtra("excursionName", "");
        intent.putExtra("excursionDate", "05/01/25"); // starting state

        try (ActivityScenario<ExcursionDetails> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.editExcursionTitle)).perform(replaceText("Test Excursion"));

            // Open the DatePickerDialog
            onView(withId(R.id.editExcursionDate)).perform(click());

            // Set the date (May 1, 2025)
            onView(withClassName(equalTo(DatePicker.class.getName())))
                    .perform(setDate(2025, 5, 1)); // Month is 1-based

            // Confirm the date picker (Android default uses "OK" button)
            onView(withText("OK")).perform(click());

            // Save the excursion
            openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            onView(withText("Save")).perform(click());

        }
    }

    @Test
    public void emptyExcursionName_showsValidationError() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ExcursionDetails.class);
        intent.putExtra("vacationID", 1);
        intent.putExtra("excursionDate", "07/15/25");

        try (ActivityScenario<ExcursionDetails> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.editExcursionTitle)).perform(replaceText("")); // Empty name
            // Open the DatePickerDialog
            onView(withId(R.id.editExcursionDate)).perform(click());

            // Set the date (May 1, 2025)
            onView(withClassName(equalTo(DatePicker.class.getName())))
                    .perform(setDate(2025, 5, 1)); // Month is 1-based

            // Confirm the date picker (Android default uses "OK" button)
            onView(withText("OK")).perform(click());
            openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            onView(withText("Save")).perform(click());
        }
    }
}