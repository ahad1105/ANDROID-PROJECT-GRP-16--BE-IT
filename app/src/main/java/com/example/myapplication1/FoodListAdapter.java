package com.example.myapplication1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Crop> foodsList;

    public FoodListAdapter(Context context, int layout, ArrayList<Crop> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtLatitude, txtLongitude,txtName;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtLatitude = (TextView) row.findViewById(R.id.txtLatitude);
            holder.txtLongitude = (TextView) row.findViewById(R.id.txtLongitude);
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Crop food = foodsList.get(position);

        holder.txtLatitude.setText("Latitude :"+food.getLatitude());
        holder.txtLongitude.setText("Longitude :"+food.getLongitude());
        holder.txtName.setText("Name :"+food.getName());
        byte[] foodImage = food.getImage();

        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
