package com.example.personaltravel.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.personaltravel.dao.ExcursionDAO;
import com.example.personaltravel.dao.VacationDAO;
import com.example.personaltravel.entities.Excursion;
import com.example.personaltravel.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version= 1, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;


    static VacationDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (VacationDatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext() ,VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

