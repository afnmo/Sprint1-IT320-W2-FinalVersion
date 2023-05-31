package com.example.navigationdrawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<ReturnHolder> {


    Context context;
    ReturnHolder temp_holder;

    List<RentedItems> rentModelList;
    List<DressModel> dressModelList;
    DatabaseHelper databaseHelper;

    public RentAdapter(Context context, List<DressModel> dressModelList) {
        this.dressModelList = dressModelList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);

    }

    @NonNull
    @Override
    public ReturnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_to_return, parent, false);
        return new ReturnHolder(view).linkAdapter(this);
    }

    public void onBindViewHolder(@NonNull ReturnHolder holder, int position) {
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
class ReturnHolder extends RecyclerView.ViewHolder{
    private RentAdapter rentAdapter;
    TextView rowId;
    TextView rowName;
    ImageView rowImg;
    public ReturnHolder(@NonNull View itemView) {
        super(itemView);
        rowId = itemView.findViewById(R.id.rented_item_id);
        rowName = itemView.findViewById(R.id.rented_item_name);
        rowImg = itemView.findViewById(R.id.rented_item_image);

        itemView.findViewById(R.id.rented_return_button).setOnClickListener(view -> {
            showDeleteConfirmationDialog();
        });
    }
    public ReturnHolder linkAdapter(RentAdapter adaptor){
        this.rentAdapter = adaptor;
        return this;
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(rentAdapter.context);
        builder.setMessage("Are you sure you want to return this item?");
        builder.setPositiveButton("return", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the Delete button
                int position = getAdapterPosition();
                DressModel dressBox = rentAdapter.dressModelList.remove(position);
                Toast.makeText(rentAdapter.context, dressBox.toString(), Toast.LENGTH_SHORT).show();
                int id1 = dressBox.getID();
                DressModel dressModel = new DressModel(id1);
          //  Toast.makeText(rentAdapter.context, "ID = " + id, Toast.LENGTH_SHORT).show();
                //deletePageAdapter.temp_holder.rowId.getText().charAt(4)

                boolean success = rentAdapter.databaseHelper.returnItem(dressModel);

                if(success){
//                deletePageAdapter.dressModelList.remove(position);
                    rentAdapter.notifyItemRemoved(position);
                    rentAdapter.notifyItemRangeChanged(position, rentAdapter.dressModelList.size());
                }
                else{
                    Toast.makeText(rentAdapter.context, "ID = " + id1, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(deletePageAdapter.context, "Failed to delete in database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
