package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addCityButton, deleteCityButton, confirmButton;
    EditText cityInput;
    LinearLayout bottomBar;

    int selectedIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //views
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);
        confirmButton = findViewById(R.id.confirm_button);
        bottomBar = findViewById(R.id.bottom_bar);
        cityInput = findViewById(R.id.city_input);

        //data
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        /// takes one input and coverts it into another type of input
        /// this- current activity
        /// second arg, content file
        /// third - what is represented in a single row


        //built in layer supporting selection highlighting

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        //selecting a city by tapping it
        cityList.setOnItemClickListener((parent, view, position, id) ->{
            selectedIndex = position;
            cityList.setItemChecked(position,true );
        });


        //ADD CITY -> show bottom input +confirm
        addCityButton.setOnClickListener(v -> {
            bottomBar.setVisibility(View.VISIBLE);
            cityInput.setText("");
            cityInput.requestFocus();
        });


        //CONFIRM -> add typed city
        confirmButton.setOnClickListener(v->{
            String newCity = cityInput.getText().toString().trim();


            if(newCity.isEmpty()) {
                Toast.makeText(this, "Enter a city name.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();


            //hiding input bar
            bottomBar.setVisibility(View.GONE);
            cityInput.setText("");

        });

        //DELETING CITY -> Selected
        deleteCityButton.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Tap a city to select first.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();

            //clearing selection
            selectedIndex =-1;
            cityList.clearChoices();
        });




    }
}