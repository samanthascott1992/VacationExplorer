package com.example.personaltravel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    private VacationAdapter vacationAdapter;
    private List<Vacation> allVacations = new ArrayList<>();
    private List<Excursion> allExcursions = new ArrayList<>();

    private RecyclerView vacationRecyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        System.out.println(getIntent().getStringExtra("test"));
        FloatingActionButton addVacDet = findViewById(R.id.addVacationDetails);
        addVacDet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        repository = new Repository(getApplication());

        vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        vacationAdapter = new VacationAdapter(this);
        vacationRecyclerView.setAdapter(vacationAdapter);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshList();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);

        MenuItem  searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by title or date");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterVacations(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVacations(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            refreshList();
            return false;
        });


        return true;
    }

    private void filterVacations(String query){
        vacationAdapter.setSearching(true);
        String lowerQuery = query.toLowerCase();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

        List<Vacation> vacationMatches = new ArrayList<>();
        for (Vacation vacation : allVacations){
            String name = vacation.getVacationName().toLowerCase();
            String start = vacation.getStartDate().toLowerCase();
            String end = vacation.getEndDate().toLowerCase();

            if (name.contains(lowerQuery) || start.contains(lowerQuery) || end.contains(lowerQuery)) {
                vacationMatches.add(vacation);
            }
        }

        for (Excursion excursion : allExcursions){
            String title = excursion.getExcursionName().toLowerCase();
            String date = excursion.getExcursionDate().toLowerCase();


            if (title.contains(lowerQuery) || date.contains(lowerQuery)) {
                int matchingVacationId = excursion.getVacationID();
                for (Vacation vacation: allVacations){
                    if (vacation.getVacationID() == matchingVacationId && !vacationMatches.contains(vacation))
                        vacationMatches.add(vacation);
                }

            }
        }
        vacationAdapter.setVacations(vacationMatches);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.sample){
            repository = new Repository(getApplication());
            Vacation vacation= new Vacation(0, "Saturday Basketball", "best Western", "04/15/24", "04/16/24");
            repository.insert(vacation);
            vacation= new Vacation(0, "Sunday Farm", "Scott Ranch", "04/29/24", "04/30/24");
            repository.insert(vacation);
//test
            refreshList();

            return true;
        }
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
cd
        if(item.getItemId() == R.id.menu_export){
            exportDisplayedData();
            return true;
        }

    return true;
    }
    @Override
    protected void onResume() {

        super.onResume();
        refreshList();
    }

    private void exportDisplayedData(){
        List<Vacation> displayedVacations = vacationAdapter.getDisplayedVacations();
        StringBuilder exportText = new StringBuilder();

        String now = new SimpleDateFormat("MM/dd/yyy HH:mm:ss", Locale.US).format(new Date());
        exportText.append("Report generated on: ").append(now).append("\n\n");

        for (Vacation vac : displayedVacations){
            exportText.append("Vacation: ").append(vac.getVacationName())
                    .append(" | Hotel: ").append(vac.getHotelName())
                    .append(" | From: ").append(vac.getStartDate())
                    .append(" | To: ").append(vac.getEndDate()).append("\n");

            for (Excursion exc :allExcursions){
                if (exc.getVacationID() == vac.getVacationID()){
                    exportText.append("     Excursion: ").append(exc.getExcursionName())
                            .append(" on ").append(exc.getExcursionDate()).append("\n");
                }
            }
            exportText.append("\n");
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vacation & Excursion Export");
        shareIntent.putExtra(Intent.EXTRA_TEXT, exportText.toString());
        startActivity(Intent.createChooser(shareIntent, "Export via"));
    }




    private void refreshList(){
        allVacations = repository.getmAllVacations();
        allExcursions = repository.getmAllExcursions();
        vacationAdapter.setVacations(allVacations);
        vacationAdapter.setExcursions(allExcursions);
        vacationAdapter.setSearching(false);

    }
}