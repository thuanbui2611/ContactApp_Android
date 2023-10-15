package com.example.m_hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HikeDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView hikeName, location, date, parking, length, level,description, createdAt, lastUpdated;

    private ActionBar actionBar;
    private String hikeID;
    private MyDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail);

        //setup action bar with title and back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Hike Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //get hike id from adapter
        Intent intent = getIntent();
        hikeID = intent.getStringExtra("HIKE_ID");
        //init
        dbHelper = new MyDbHelper(this);

        image = findViewById(R.id.hikeImageDT);
        hikeName = findViewById(R.id.nameHikeDT);
        location = findViewById(R.id.locationDT);
        date = findViewById(R.id.dateDT);
        parking = findViewById(R.id.parkingDT);
        length = findViewById(R.id.lengthDT);
        level = findViewById(R.id.levelDT);
        description = findViewById(R.id.descriptionDT);
        createdAt = findViewById(R.id.createdAtDT);
        lastUpdated = findViewById(R.id.lastUpdatedDT);

        showHikeDetails();
    }

    private void showHikeDetails() {
        //query to select record by id
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID +" =\"" + hikeID+"\"";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int idColumnIndex = cursor.getColumnIndex(Constants.C_ID);
                @SuppressLint("Range") int nameColumnIndex = cursor.getColumnIndex(Constants.C_NAME);
                @SuppressLint("Range") int locationColumnIndex = cursor.getColumnIndex(Constants.C_LOCATION);
                @SuppressLint("Range") int parkingColumnIndex = cursor.getColumnIndex(Constants.C_PARKING_AVAILABLE);
                @SuppressLint("Range") int dateColumnIndex = cursor.getColumnIndex(Constants.C_DATE);
                @SuppressLint("Range") int lengthColumnIndex = cursor.getColumnIndex(Constants.C_LENGTH);
                @SuppressLint("Range") int levelColumnIndex = cursor.getColumnIndex(Constants.C_LEVEL);
                @SuppressLint("Range") int desColumnIndex = cursor.getColumnIndex(Constants.C_DESCRIPTION);
                @SuppressLint("Range") int imageColumnIndex = cursor.getColumnIndex(Constants.C_IMAGE);
                @SuppressLint("Range") int createdAtColumnIndex = cursor.getColumnIndex(Constants.C_CREATED_AT);
                @SuppressLint("Range") int lastUpdatedColumnIndex = cursor.getColumnIndex(Constants.C_LAST_UPDATED);
                String id = ""+cursor.getInt(idColumnIndex);
                String name = ""+cursor.getString(nameColumnIndex);
                String locationGet = ""+cursor.getString(locationColumnIndex);
                String parkingGet = ""+cursor.getString(parkingColumnIndex);
                String dateGet = ""+cursor.getString(dateColumnIndex);
                String lengthGet = ""+cursor.getString(lengthColumnIndex);
                String levelGet = ""+cursor.getString(levelColumnIndex);
                String descriptionGet = ""+cursor.getString(desColumnIndex);
                String imageHike = ""+cursor.getString(imageColumnIndex);
                String createdAtGet = ""+cursor.getString(createdAtColumnIndex);
                String lastUpdatedGet = ""+cursor.getString(lastUpdatedColumnIndex);

                //convert timestamp to dd/mm/yyyy hh:mm aa
                Calendar c1 = Calendar.getInstance(Locale.getDefault());
                c1.setTimeInMillis(Long.parseLong(createdAtGet));
                String createdAtTime = (String) DateFormat.format("dd/MM/yyyy hh:mm:aa", c1);

                Calendar c2 = Calendar.getInstance(Locale.getDefault());
                c1.setTimeInMillis(Long.parseLong(lastUpdatedGet));
                String lastUpdatedTime = (String) DateFormat.format("dd/MM/yyyy hh:mm:aa", c1);


                hikeName.setText(name);
                location.setText(locationGet);
                date.setText(dateGet);
                parking.setText(parkingGet);
                length.setText(lengthGet);
                level.setText(levelGet);
                description.setText(descriptionGet);
                createdAt.setText(createdAtTime);
                lastUpdated.setText(lastUpdatedTime);

                if(image.equals("null")){
                    image.setImageResource(R.drawable.ic_image_hike);
                } else {
                    image.setImageURI(Uri.parse(imageHike));
                }
            } while (cursor.moveToNext());
        }
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); // go to prev activity
        return super.onSupportNavigateUp();
    }
}