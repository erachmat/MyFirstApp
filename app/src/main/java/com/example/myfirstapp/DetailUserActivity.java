package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.databinding.ActivityDetailUserBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailUserActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    View view;
    private ActivityDetailUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        binding.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat object dari kelas MyAsyncTasks dan mengeksekusinya
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
            }
        });
    }

    private class MyAsyncTasks extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Menampilkan progress dialog
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // Implementasi API di background dan simpan response di variable current
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    String apiUrl = "https://randomuser.me/api/";
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }

                    // Mengembalikan data ke method onPostExecute
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.e("data", s);

            // Sembunyikan progress dialog setelah menerima data dari API
            progressDialog.dismiss();

            try {

                // Parsing JSON dari data
                JSONObject jsonObject = new JSONObject(s);

                // Ambil daftar results dari JSONArray
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject oneObject = jsonArray.getJSONObject(0);

                // Ambil nama depan
                JSONObject fullName = oneObject.getJSONObject("name");
                String firstName = fullName.getString("first");

                // Ambil email
                String email = oneObject.getString("email");

                // Ambil URL image
                JSONObject downloadedImage = oneObject.getJSONObject("picture");
                String image = downloadedImage.getString("medium");

                // Menampilkan data ke dalam UI
                binding.txtName.setText("Full Name : " + firstName);
                binding.txtEmail.setText("Email : " + email);

                // Menggunakan library picasso untuk menampilkan image dari URL
                Picasso.with(getApplicationContext())
                        .load(image)
                        .into(binding.imgUser);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}