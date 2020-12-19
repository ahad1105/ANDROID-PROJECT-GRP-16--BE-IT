package com.example.myapplication1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CropsAdapter extends RecyclerView.Adapter<CropsAdapter.CropsViewHolder> {
    private String[] data;

    private OnCropListener onCropListener;
    public CropsAdapter(String[] data, OnCropListener onCropListener) {
        this.data = data;
        this.onCropListener = onCropListener;
    }
//    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            int itemPosition = RecyclerView.getChildLayoutPosition(view);
//            String item = data[itemPosition];
//            //Toast.makeText(, item, Toast.LENGTH_SHORT).show();
//        }
//    };

    @NonNull
    @Override
    public CropsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new CropsViewHolder(view, onCropListener);
    }

    @Override
    public void onBindViewHolder(CropsViewHolder holder, int position) {
        String title = data[position];
        holder.tvTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CropsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        ImageView imgItem;
        OnCropListener onCropListener;
        public CropsViewHolder(View itemView, OnCropListener onCropListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgItem = itemView.findViewById(R.id.imgItem);
            this.onCropListener = onCropListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCropListener.onCropClick(getAdapterPosition());
        }
    }

    public interface OnCropListener {
        void onCropClick(int position);
    }

}
