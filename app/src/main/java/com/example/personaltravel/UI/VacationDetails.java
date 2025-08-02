package com.example.personaltravel.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltravel.R;
import com.example.personaltravel.database.Repository;
import com.example.personaltravel.entities.Excursion;
import com.example.personaltravel.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class VacationDetails extends AppCompatActivity {
    Repository repository;
    String title;
    String hotelName;
    String vacationStartDate;
    String vacationEndDate;
    int vacationID;
    Vacation currentVacation;
    int numExcursions;
    EditText editVacationName;
    EditText editHotelName;
    TextView editStartDate;
    TextView editEndDate;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

    List<Excursion> filteredExcursions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        editVacationName = findViewById(R.id.editVacationTitle);
        editHotelName = findViewById(R.id.editHotelName);
        vacationID = getIntent().getIntExtra("id", -1);
        editStartDate = findViewById(R.id.editStarDate);
        editEndDate = findViewById(R.id.editEndDate);
        title = getIntent().getStringExtra("title");
        hotelName = getIntent().getStringExtra("hotelName");
        vacationStartDate = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");

        editVacationName.setText(title);
        editHotelName.setText(hotelName);
        editStartDate.setText(vacationStartDate);
        editEndDate.setText(vacationEndDate);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (Excursion p : repository.getmAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (startDate != null) {
            try {
                Date startDate = sdf.parse(vacationStartDate);
                Date endDate = sdf.parse(vacationEndDate);
                myCalendarStart.setTime(startDate);
                myCalendarEnd.setTime(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editStartDate = findViewById(R.id.editStarDate);
        editEndDate = findViewById(R.id.editEndDate);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "07/12/2025";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar instance to whatever is selected
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "08/02/2025";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //sets the calendar instance to whatever is selected
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    //updates the date displayed after it has been chosen
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.saveVacation) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStart.getTime());
            String endDateString = sdf.format(myCalendarEnd.getTime());

            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "End date must be AFTER start date", Toast.LENGTH_LONG).show();
                } else {
                    Vacation vacation;
                    if (vacationID == -1) {
                        if (repository.getmAllVacations().size() == 0) vacationID = 1;
                        else
                            vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                        vacation = new Vacation(vacationID, editVacationName.getText().toString().trim(), editHotelName.getText().toString().trim(), editStartDate.getText().toString().trim(), editEndDate.getText().toString().trim());
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        try {
                            vacation = new Vacation(vacationID, editVacationName.getText().toString().trim(), editHotelName.getText().toString().trim(), editStartDate.getText().toString().trim(), editEndDate.getText().toString().trim());
                            repository.update(vacation);
                            this.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (item.getItemId() == R.id.deleteVacation) {
            for (Vacation vac : repository.getmAllVacations()) {
                if (vac.getVacationID() == vacationID) currentVacation = vac;
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            //if the vacation has any associated excursions, prevent deletion of the vacation, otherwise delete it
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Shared");
            StringBuilder shareData = new StringBuilder();
            shareData.append("Vacation: " + editVacationName.getText().toString() + "\n");
            shareData.append("Hotel name: " + editHotelName.getText().toString() + "\n");
            shareData.append("Start Date: " + editStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editEndDate.getText().toString() + "\n");
            for (int i = 0; i < filteredExcursions.size(); i++) {
                shareData.append("Excursion " + (i + 1) + ": " + filteredExcursions.get(i).getExcursionName() + "\n");
                shareData.append("Excursion " + (i + 1) + " Date: " + filteredExcursions.get(i).getExcursionDate() + "\n");
            }
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }


        if (item.getItemId() == R.id.sampleExcursion) {
            Excursion excursion = new Excursion(0, "Hike Everest", "07/15/25", vacationID);
            repository.insert(excursion);
            excursion = new Excursion(0, "Swim with sharks", "05/26/25", vacationID);
            repository.insert(excursion);
            RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
            final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
            recyclerView.setAdapter(excursionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Excursion> filteredParts = new ArrayList<>();
            for (Excursion p : repository.getmAllExcursions()) {
                if (p.getVacationID() == vacationID) filteredParts.add(p);
            }
            excursionAdapter.setExcursions(filteredParts);
            return true;

        }


        if (item.getItemId() == R.id.vacationStartNotify) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //sets alert for End Date
        if (item.getItemId() == R.id.vacationEndAlert) {
            String dateFromScreen = editEndDate.getText().toString();
            String alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }

        //sets alert for both Start Date and End Date
        if (item.getItemId() == R.id.vacationBothAlert) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "Vacation " + title + " is starting";
            alertPicker(dateFromScreen, alert);
            dateFromScreen = editEndDate.getText().toString();
            alert = "Vacation " + title + " is ending";
            alertPicker(dateFromScreen, alert);

            return true;
        }
        return true;
    }
    public void alertPicker(String dateFromScreen, String alert) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);

        intent.putExtra("key", alert);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
//        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        numAlert = rand.nextInt(99999);
        System.out.println("numAlert Vacation = " + numAlert);

    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredParts = new ArrayList<>();
        for (Excursion p : repository.getmAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredParts.add(p);
        }
        excursionAdapter.setExcursions(filteredParts);
//        Toast.makeText(VacationDetails.this,"refresh list", Toast.LENGTH_LONG).show();

    }
}

