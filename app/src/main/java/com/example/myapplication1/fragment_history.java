package com.example.myapplication1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_history extends Fragment {
    GridView gridView;
    ArrayList<Crop> list;
    FoodListAdapter adapter = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new FoodListAdapter(getActivity().getApplicationContext(),R.layout.design_grid,list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity().getApplicationContext(), "CropDB.sqlite", null, 1);
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM CROP");
        //Cursor cursor = ResultActivity.sqLiteHelper.getData("SELECT * FROM CROP");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String Longitude = cursor.getString(1);
            String Latitude = cursor.getString(2);
            String Name = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new Crop(id, Latitude,Longitude,Name,image));
        }
        adapter.notifyDataSetChanged();
        return view;
    }
}
