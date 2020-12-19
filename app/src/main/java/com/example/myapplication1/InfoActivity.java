package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {
    ScrollView svInfo;
    TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        svInfo = findViewById(R.id.svInfo);
        tvInfo = findViewById(R.id.tvInfo);

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "banana.txt");
        map.put(1, "cabbage.txt");
        map.put(2, "chilly.txt");
        map.put(3, "corn.txt");
        map.put(4, "cotton.txt");
        map.put(5, "cucumber.txt");
        map.put(6, "mustard.txt");
        map.put(7, "onion.txt");
        map.put(8, "rice.txt");
        map.put(9, "sugarcane.txt");
        Intent a = getIntent();
        Bundle b = a.getExtras();
        int position = b.getInt("position");

        String file_name = map.get(position);
        StringBuffer sb = new StringBuffer();
        String line = "";

        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open(file_name));
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            tvInfo.setText(sb.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
