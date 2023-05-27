package com.example.navigationdrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.BitSet;
import java.util.List;


public class DressAdaptor extends RecyclerView.Adapter<DressAdaptor.ViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<DressModel> dressModelList;
    RecyclerView rvPrograms;
    final View.OnClickListener onClickListener = new MyOnClickListener();



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView rowId;
        TextView rowName;
        ImageView rowImg;
        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            rowId = itemView.findViewById(R.id.item_id);
            rowName = itemView.findViewById(R.id.item_name);
            rowImg = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }



    }

    public DressAdaptor(Context context, List<DressModel> dressModelList, RecyclerView rvPrograms, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.dressModelList = dressModelList;
        this.rvPrograms = rvPrograms;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public DressAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.temp, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view, recyclerViewInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DressAdaptor.ViewHolder holder, int position) {
        DressModel dressModel = dressModelList.get(position);
        holder.rowId.setText("" +dressModel.getID());
        holder.rowName.setText(""+dressModel.getName());
        holder.rowImg.setImageBitmap(dressModel.getImage());
    }

    @Override
    public int getItemCount() {
        return dressModelList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = rvPrograms.getChildLayoutPosition(view);
            String item = dressModelList.get(itemPosition).getName();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();

        }
    }



}