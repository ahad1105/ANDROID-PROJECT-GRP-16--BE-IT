package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_info extends Fragment implements CropsAdapter.OnCropListener {
    RecyclerView rvCrops;
    String[] crops;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        rvCrops = (RecyclerView) view.findViewById(R.id.rvCrops);
        rvCrops.setLayoutManager(new LinearLayoutManager(getActivity()));
        crops = new String[]{"Banana", "Cabbage", "Chilly", "Corn", "Cotton", "Cucumber", "Mustard", "Onion", "Rice", "Sugarcane"};
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rvCrops.setAdapter(new CropsAdapter(crops, this));
        return view;
    }

    @Override
    public void onCropClick(int position) {
        String c = crops[position];
        Intent a = new Intent(getActivity(), InfoActivity.class);
        a.putExtra("position", position);
        startActivity(a);
    }
}

