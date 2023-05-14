package com.example.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeletePageAdapter extends RecyclerView.Adapter<DeleteHolder> {


    Context context;
    DeleteHolder temp_holder;

    List<DressModel> dressModelList;
    DatabaseHelper databaseHelper;

    public DeletePageAdapter(Context context, List<DressModel> dressModelList) {
        this.dressModelList = dressModelList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public DeleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_to_delete, parent, false);
        return new DeleteHolder(view).linkAdapter(this);
    }

    public void onBindViewHolder(@NonNull DeleteHolder holder, int position) {
        temp_holder = holder;
        DressModel dressModel = dressModelList.get(position);
        holder.rowId.setText("" +dressModel.getID());
        holder.rowName.setText(""+dressModel.getName());
        holder.rowImg.setImageBitmap(dressModel.getImage());

    }
    @Override
    public int getItemCount() {
        return dressModelList.size();
    }
}
class DeleteHolder extends RecyclerView.ViewHolder{
    private DeletePageAdapter deletePageAdapter;
    TextView rowId;
    TextView rowName;
    ImageView rowImg;
    public DeleteHolder(@NonNull View itemView) {
        super(itemView);
        rowId = itemView.findViewById(R.id.item_id);
        rowName = itemView.findViewById(R.id.item_name);
        rowImg = itemView.findViewById(R.id.item_image);

        itemView.findViewById(R.id.deleteButton).setOnClickListener(view -> {

            int position = getAdapterPosition();
//
            DressModel dressBox = deletePageAdapter.dressModelList.remove(position);
            int id = dressBox.getID();
            DressModel dressModel = new DressModel(id);
//            Toast.makeText(deletePageAdapter.context, "ID = " + id, Toast.LENGTH_SHORT).show();
            //deletePageAdapter.temp_holder.rowId.getText().charAt(4)

            boolean success = deletePageAdapter.databaseHelper.DeleteOne(dressModel);

            if(success){
//                deletePageAdapter.dressModelList.remove(position);
                deletePageAdapter.notifyItemRemoved(position);
                deletePageAdapter.notifyItemRangeChanged(position, deletePageAdapter.dressModelList.size());
            }
            else{
                Toast.makeText(deletePageAdapter.context, "ID = " + id, Toast.LENGTH_SHORT).show();
                //Toast.makeText(deletePageAdapter.context, "Failed to delete in database", Toast.LENGTH_SHORT).show();
            }


        });
    }
    public DeleteHolder linkAdapter(DeletePageAdapter adaptor){
        this.deletePageAdapter = adaptor;
        return this;
    }


}
