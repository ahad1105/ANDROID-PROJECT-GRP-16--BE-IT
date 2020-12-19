package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    TextView tvCropName, tvLocation, tvDescription;
    ScrollView svDescription;
    Button btnShare;
    ImageView ivCrop;
    Bitmap bitmap;

    FirebaseDatabase database;
    DatabaseReference myref;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvCropName = findViewById(R.id.tvCropName);
        tvLocation = findViewById(R.id.tvLocation);
        svDescription = findViewById(R.id.svDescription);
        btnShare = findViewById(R.id.btnShare);
        tvDescription = findViewById(R.id.tvDescription);
        ivCrop = findViewById(R.id.ivCrop);

        database=FirebaseDatabase.getInstance();
        myref=database.getReference("Firebase");


        Map<Integer, String> map = new HashMap<>();
        map.put(0, "cabbage.txt");
        map.put(1, "banana.txt");
        map.put(2, "chilly.txt");
        map.put(3, "corn.txt");
        map.put(4, "cotton.txt");
        map.put(5, "cucumber.txt");
        map.put(6, "mustard.txt");
        map.put(7, "onion.txt");
        map.put(8, "rice.txt");
        map.put(9, "sugarcane.txt");
        map.put(10, "not a crop.txt");

        final Intent a = getIntent();
        final Bundle extras = a.getExtras();

        if(getIntent().hasExtra("image")) {
            bitmap = BitmapFactory.decodeByteArray(
                    a.getByteArrayExtra("image"),0,getIntent().getByteArrayExtra("image").length);
            ivCrop.setImageBitmap(bitmap);
            double lang = extras.getDouble("lon");
            double lati = extras.getDouble("lat");

            sqLiteHelper = new SQLiteHelper(this, "CropDB.sqlite", null, 1);
            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CROP(Id INTEGER PRIMARY KEY AUTOINCREMENT,latitude VARCHAR,longitude VARCHAR, name VARCHAR, image BLOB)");
            sqLiteHelper.insertData(String.valueOf(lang).trim(),String.valueOf(lati).trim(), extras.getString("crop"),a.getByteArrayExtra("image"));

        }
        String result= extras.getString("crop");
        double lon = extras.getDouble("lon");
        double lat = extras.getDouble("lat");
        String cropname=extras.getString("crop");
        cropname = cropname.replaceAll("\\\\[.*?\\\\]","");
        cropname = cropname.replaceAll("\\[", "");
        String[] Arr = cropname.split(",");
        String res= Arr[0].toString();

        Firebase f=new Firebase(lat,lon,res.substring(5, res.length()-1));
        myref.push().setValue(f);
        int position;
        if (res.charAt(1) != '0') {
            position = Integer.parseInt(res.substring(0, 1));
        }
        else {
            position = Character.getNumericValue(result.charAt(2));
        }
        String file_name = map.get(position);
        StringBuffer sb = new StringBuffer();
        String line = "";

        Toast.makeText(this, res, Toast.LENGTH_LONG).show();

        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open(file_name));
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            tvDescription.setText(sb.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Geocoder geocoder = new Geocoder(ResultActivity.this, Locale.ENGLISH);
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            Address address = addressList.get(0);
            final String msg = address.getCountryName() + " " + address.getAdminArea() + " " + address.getSubAdminArea() +
                    " " + address.getLocality() + " " + address.getSubLocality() + " " + address.getThoroughfare()
                    + " " + address.getSubThoroughfare() + " " + address.getPostalCode();
            tvLocation.setText("Location:\n" + msg);
        }
        catch (Exception e) {
            tvLocation.setText("Location:\n" + "Longitude:" + lon + "\nLatitude: " + lat);
            e.printStackTrace();
        }

        // Setting text of tvResult logic.
        if (position < 10)
            if (res.charAt(res.length() - 1) == ']')
                tvCropName.setText("Result:\n" + res.substring(5, res.length() - 1));
            else
                tvCropName.setText("Result:\n" + res.substring(5));
        else
            if (res.charAt(res.length() - 1) == ']')
                tvCropName.setText("Result:\n" + res.substring(7, res.length() - 1));
            else
                tvCropName.setText("Result:\n" + res.substring(7));
        //tvCropName.setText("Result:\n" + res);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File(getExternalCacheDir(), "p1.png");
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Intent a = new Intent(Intent.ACTION_SEND);
                String msg = "Crop: " + extras.getString("crop") + "\nLocation:\n" + "Longitude:" +
                        extras.getDouble("lon") + " "
                        + "Latitude: " + extras.getDouble("lat");
                a.putExtra(Intent.EXTRA_TEXT, msg);
                a.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                a.setType("image/*");
                startActivity(a);
            }
        });
    }

//    public void shareImage(View v) {
//        File f = new File(uri.getPath());
//        Intent i = new Intent(Intent.ACTION_SEND);
//        Uri myUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", f);
//        i.setDataAndType(myUri, "image/*");
//        i.putExtra(Intent.EXTRA_STREAM, myUri);
//        startActivity(i);
//    }
}

