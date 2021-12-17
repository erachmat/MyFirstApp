package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomListActivity extends AppCompatActivity {

    // Array of strings...
    ListView simpleList;
    String[] universityList = {"Amikom", "UPN", "UII",
            "UGM", "UAD", "UMY"};
    int[] logos = {R.drawable.logoo_amikom, R.drawable.logo_upn, R.drawable.logo_uii,
            R.drawable.logo_ugm, R.drawable.logo_uad, R.drawable.logo_umy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        simpleList = (ListView) findViewById(R.id.simpleListView);

        CustomAdapter customAdapter = new CustomAdapter(this,
                universityList, logos);

        simpleList.setAdapter(customAdapter);
    }

    private class CustomAdapter extends BaseAdapter {

        Context context;
        String[] universityList;
        int[] logos;
        LayoutInflater layoutInflater;

        public CustomAdapter(Context context, String[] universityList, int[] logos) {
            this.context = context;
            this.universityList = universityList;
            this.logos = logos;
            layoutInflater = (LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return universityList.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            view = layoutInflater.inflate(R.layout.activity_custom_listview, null);
            TextView txtView = view.findViewById(R.id.textView);
            ImageView img = view.findViewById(R.id.icon);

            txtView.setText(universityList[position]);
            img.setImageResource(logos[position]);

            return view;
        }
    }
}