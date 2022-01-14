package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.myfirstapp.databinding.ActivityDatabaseBinding;
import com.example.myfirstapp.databinding.ActivityWeatherBinding;

public class DatabaseActivity extends AppCompatActivity {

    View view;
    ActivityDatabaseBinding binding;

    // Deklarasi class myDbAdapter
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting binding untuk layout
        binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        // Panggil class myDbAdapter
        helper = new myDbAdapter(this);
    }

    // Method yang digunakan untuk mengambil data username dan password
    // dari Form layout untuk dimasukkan ke dalam database SQLite
    public void addUser(View view) {

        // Menyimpan username dan password ke dalam variable
        String t1 = binding.editName.getText().toString();
        String t2 = binding.editPass.getText().toString();

        // digunakan untuk memvalidasi apakah t1
        // atau t2 itu valuenya kosong atau tidak
        if (t1.isEmpty() || t2.isEmpty()) {

            // Pesan peringatan
            Toast.makeText(getApplicationContext(),"Enter Both Name and Password",
                    Toast.LENGTH_LONG).show();
        }
        else {

            // Panggil fungsi insertData dari class myDbAdapter
            // Masukkan t1 dan t2 sebagai parameter
            long id = helper.insertData(t1, t2);

            // validasi apakah datanya berhasil tersimpan ke dalam SQlite
            if(id <= 0) {

                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",
                        Toast.LENGTH_LONG).show();

            }
            else {

                // Pesan pemberitahuan bahwa data user berhasil
                // di simpan ke dalam table
                Toast.makeText(getApplicationContext(),"Insertion Successful",
                        Toast.LENGTH_LONG).show();

            }

            binding.editName.setText("");
            binding.editPass.setText("");
        }
    }

    // Method digunakan untuk menampilkan semua
    // data user dari table di SQLite
    public void viewdata(View view) {

        // Panggil fungsi getData dari class myDbAdapter
        String data = helper.getData();

        Toast.makeText(getApplicationContext(), data,
                Toast.LENGTH_LONG).show();
    }
}