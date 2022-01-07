package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myfirstapp.Model.GsonParse;
import com.example.myfirstapp.Model.User;
import com.example.myfirstapp.databinding.ActivityDetailUserBinding;
import com.example.myfirstapp.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    View view;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        UserPreference pref = new UserPreference(this);
        if(!pref.checkData().equals("")){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.edtName.getText().toString().equals("") &&
                        !binding.edtPassword.getText().toString().equals("")) {

                    postHttpResponse(binding.edtName.getText().toString(),
                            binding.edtPassword.getText().toString());
                }
            }
        });
    }

    private void postHttpResponse(String name, String password) {

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://newbiedev.xyz/project/login.php";

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {

                    Gson gson = new Gson();
                    GsonParse gsonObj = gson.fromJson(response.body().string(), GsonParse.class);

                    UserPreference pref = new UserPreference(LoginActivity.this);

                    User user = gsonObj.getUser();
                    pref.setName(user.getName());
                    pref.setEmail(user.getEmail());
                    pref.setKey(user.getUserKey());

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"Error, User/Password salah!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }
}