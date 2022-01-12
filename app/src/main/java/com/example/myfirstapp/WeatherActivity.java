package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myfirstapp.databinding.ActivityWeatherBinding;

public class WeatherActivity extends AppCompatActivity {

    View view;
    ActivityWeatherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting binding untuk layout
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        if(getIntent().getExtras() != null) {

            binding.txtSuhu.setText(getIntent().getStringExtra("suhuCelcius") + " C");
            binding.txtKota.setText(getIntent().getStringExtra("namaKota"));
            binding.txtCuaca.setText(getIntent().getStringExtra("kondisiCuaca"));
        }
    }
}