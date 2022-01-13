package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Model.Cuaca;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // public static final + type data + nama var
    // nama konstanta harus huruf kapital dengan spasi diganti underscore
    // kemudian diberikan kunci
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("TOKEN_UPDATE", "Fetching FCM registration token failed",
                                    task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("TOKEN_UPDATE", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        Button btnKonteks = findViewById(R.id.btnKonteks);
        registerForContextMenu(btnKonteks);

        /** Mengatur tampilan dari MainActivity berasal dari file
        activity_main.xml **/
    }

    /** Method ini digunakan untuk mengatur context menu dari file activity_menu*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.listview:
                Toast.makeText(getApplicationContext(), "ListView",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.custom_listview:
                Toast.makeText(getApplicationContext(), "Custom ListView",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.register:
                Toast.makeText(getApplicationContext(), "Register",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    /** Method ini digunakan untuk mengatur option menu dari file activity_menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.listview){
            startActivity(new Intent(this, ListActivity.class));
        } else if (item.getItemId() == R.id.custom_listview) {
            startActivity(new Intent(this, CustomListActivity.class));
        } else if (item.getItemId() == R.id.register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        return true;
    }

    /** Dipanggil ketika user mengetuk tombol send */
    public void sendMessage(View view) {
        /** Berikan aksi sebagai respon kepada tombol */

        // Membuat intent baru dari MainActivity ke DisplayMessageActivity
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editText = findViewById(R.id.txtMessage);
        // mencari view (edittext) yang id nya ada txtMessage

        String message = editText.getText().toString();
        // pertama buat var message isinya adalah
        // inputan dari user pada editText txtMessage

        intent.putExtra(EXTRA_MESSAGE, message);
        // memasukkan var message ke dalam intent yang sudah dibuat

        startActivity(intent);
        // memulai activity yang baru
        // dan membawa data extra
    }

    /** Dipanggil ketika user mengetuk tombol listview */
    public void showList(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    /** Dipanggil ketika user mengetuk tombol custom listview */
    public void showCustomList(View view){
        Intent intent = new Intent(this, CustomListActivity.class);
        startActivity(intent);
    }

    /**
     * Dipanggil ketika user mengetuk tombol form register
     */
    public void showFormRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Dipanggil ketika user mengetuk tombol photos
     */
    public void showListPhotos(View view) {
        Intent intent = new Intent(this, ListPhotosActivity.class);
        startActivity(intent);
    }

    /**
     * Dipanggil ketika user mengetuk tombol detail movie
     */
    public void showDetailUser(View view) {
        Intent intent = new Intent(this, DetailUserActivity.class);
        startActivity(intent);
    }

    /**
     * Dipanggil ketika user mengetuk tombol login Form
     */
    public void showLoginForm(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Dipanggil ketika user mengetuk tombol weather
     */
    public void showWeather(View view) {

        OkHttpClient httpClient = new OkHttpClient();

        // URL endpoint tempat penyimpanan file login.php
        String url = "http://api.openweathermap.org/data/2.5/weather?" +
                "q=Sleman&appid=920bd14cbeee3b1f961b7c53351af103";

        // Membuat struktur request
        Request request = new Request.Builder()
                .url(url)
                .build();

       httpClient.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Toast.makeText(getApplicationContext(),
                        "Tidak dapat terhubung server", Toast.LENGTH_LONG).show();
                e.printStackTrace();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {

               // Merubah response body JSON menjadi
              // file object Java namanya Cuaca
                Gson gson = new Gson();
                Cuaca cuaca = gson.fromJson(response.body().string(), Cuaca.class);

                // Ambil data Temperatur dari API
               // Convert menjadi satuan Celcius
               Double suhuKelvin = cuaca.getMain().getTemp();
               Double suhuCelcius = suhuKelvin - 273.15;

               String namaKota = cuaca.getName(); // Ambil data NAMA KOTA dari API

               // Ambil data KONDISI CUACA dari API
               String kondisiCuaca = cuaca.getWeather().get(0).getDescription();

               // Kirim 3 variable hasil dari JSON
               // melalui intent menuju WeatherActivity
               Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
               intent.putExtra("suhuCelcius", String.format("%.2f", suhuCelcius));
               intent.putExtra("namaKota", namaKota);
               intent.putExtra("kondisiCuaca", kondisiCuaca);
               startActivity(intent);
           }
       });

    }

    /**
     * Dipanggil ketika user mengetuk tombol login Form
     */
    public void showDatabase(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
}