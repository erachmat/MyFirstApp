package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    // Array of strings...
    ListView simpleList;
    // deklarasi data array sebagai sumbernya
    String[] universityList = {"Amikom", "UPN", "UII",
            "UGM", "UAD", "UMY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mengatur tampilan activity dari file layout xml
        setContentView(R.layout.activity_list);

        // deklarasi listview sebagai wadah
        simpleList = (ListView) findViewById(R.id.simpleListView);

        // deklarasi adapter penghubung antara data dengan view
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, R.id.textView, universityList);

        // atur listview menggunakan adapter yang sudah dibuat
        simpleList.setAdapter(arrayAdapter);
    }
}