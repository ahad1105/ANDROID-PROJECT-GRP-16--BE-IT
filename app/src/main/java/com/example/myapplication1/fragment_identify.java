package com.example.myapplication1;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


public class fragment_identify extends Fragment{
    ImageView imgPhoto;
    Button btnIdentify;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_identify, container, false);
//        ImageView imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        Button btnIdentify = (Button) view.findViewById(R.id.btnIdentify);

        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PhotoActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}

