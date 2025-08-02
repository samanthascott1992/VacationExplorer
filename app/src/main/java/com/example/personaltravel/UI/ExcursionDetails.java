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

import com.example.personaltravel.R;
import com.example.personaltravel.database.Repository;
import com.example.personaltravel.entities.Excursion;
import com.example.personaltravel.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExcursionDetails extends AppCompatActivity {
    Repository repository;
    String excursionName;
//    String excursionDate;
    String excursionNote;
    int vacationID;
    int excursionID;
    EditText editExcursionName;
    TextView editExcursionDate;
    Excursion currentExcursion;
//    EditText editExcursionNote;
    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendarDate = Calendar.getInstance();
    String setDate;
    Random rand = new Random();
    int numAlert = rand.nextInt(99999);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository=new Repository(getApplication());
        excursionName = getIntent().getStringExtra("excursionName");
        editExcursionName=findViewById(R.id.editExcursionTitle);
        editExcursionName.setText(excursionName);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        excursionID = getIntent().getIntExtra("id", -1);
        setDate = getIntent().getStringExtra("excursionDate");



        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (setDate != null) {
            try {
                Date excursionDate = sdf.parse(setDate);
                myCalendarDate.setTime(excursionDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        editExcursionDate=findViewById(R.id.editExcursionDate);

        editExcursionDate.setText(setDate);
//        editExcursionNote.setText(excursionNote);



        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info=editExcursionDate.getText().toString().trim();
                if(info.equals(""))info="07/15/2025";
                try{
                    myCalendarDate.setTime(sdf.parse(info));
                }catch(ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog((ExcursionDetails.this), excursionDate, myCalendarDate.get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH), myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
    }

    private void updateLabelStart(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    if (item.getItemId() == android.R.id.home) {
        this.finish();
        return true;
    }

    // ✅ SAVE EXCURSION
    if (item.getItemId() == R.id.excursionSave) {
        String name = editExcursionName.getText().toString().trim();
        String date = editExcursionDate.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Name and date cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        }

        Vacation vacation = null;
        List<Vacation> vacations = repository.getmAllVacations();
        for (Vacation vac : vacations) {
            if (vac.getVacationID() == vacationID) {
                vacation = vac;
                break;
            }
        }

        if (vacation == null) {
            Toast.makeText(this, "Vacation not found", Toast.LENGTH_SHORT).show();
            return true;
        }

        try {
            Date excursionDate = sdf.parse(date);
            Date startDate = sdf.parse(vacation.getStartDate());
            Date endDate = sdf.parse(vacation.getEndDate());

            if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                Toast.makeText(this, "Excursion Date must be within the Vacation's Start and End dates", Toast.LENGTH_LONG).show();
                return true;
            }

            Excursion excursion;
            if (excursionID == -1) {
                List<Excursion> allExcursions = repository.getmAllExcursions();
                excursionID = allExcursions.isEmpty() ? 1 : allExcursions.get(allExcursions.size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, name, date, vacationID);
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionID, name, date, vacationID);
                repository.update(excursion);
            }

            this.finish();

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    if (item.getItemId() == R.id.excursionDelete) {
        for (Excursion excursion : repository.getmAllExcursions()) {
            if (excursion.getExcursionID() == excursionID) {
                currentExcursion = excursion;
                break;
            }
        }

        if (currentExcursion != null) {
            repository.delete(currentExcursion);
            Toast.makeText(this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Excursion not found", Toast.LENGTH_SHORT).show();
        }

        this.finish();
        return true;
    }


    if (item.getItemId() == R.id.excursionNotify) {
        String dateFromScreen = editExcursionDate.getText().toString().trim();
        String alert = "Excursion " + (excursionName != null ? excursionName : editExcursionName.getText().toString()) + " is today";

        if (dateFromScreen.isEmpty()) {
            Toast.makeText(this, "Excursion date is empty", Toast.LENGTH_SHORT).show();
            return true;
        }

        try {
            Date myDate = sdf.parse(dateFromScreen);
            if (myDate == null) {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return true;
            }

            long trigger = myDate.getTime();
            Intent intent = new Intent(this, MyReceiver.class);
            intent.putExtra("key", alert);

            PendingIntent sender = PendingIntent.getBroadcast(this, numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            Toast.makeText(this, "Notification set for " + dateFromScreen, Toast.LENGTH_SHORT).show();

            numAlert = rand.nextInt(99999);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse date", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    return super.onOptionsItemSelected(item);
}








}