package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myfirstapp.databinding.ActivityHomeBinding;
import com.example.myfirstapp.databinding.ActivityLoginBinding;

public class HomeActivity extends AppCompatActivity {

    View view;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        // Panggil file SharedPreferences
        UserPreference pref = new UserPreference(this);

        // Tampilkan data dari SharedPreferences
        // ke masing-masing textView
        binding.txtName.setText(pref.getName());
        binding.txtEmail.setText(pref.getEmail());
        binding.txtKey.setText(pref.getKey());
    }

    // Setting file menu yang akan ditampilkan pada
    // halaman Home
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    // Memberikan action jika item menu di klik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                // Panggil sharedPreference
                UserPreference pref = new UserPreference(this);
                // jalankan fungsi hapus data
                pref.removeData();

                // redirect ke halaman login
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));

                // tutup halaman Home
                finish();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }
}